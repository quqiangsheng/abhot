/*
 * Copyright 2016 KairosDB Authors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.max256.abhot.core.telnet;

import com.google.common.collect.ImmutableSortedMap;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jboss.netty.channel.Channel;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.DataPointSet;
import com.max256.abhot.core.datapoints.DoubleDataPointFactory;
import com.max256.abhot.core.datapoints.LongDataPointFactory;
import com.max256.abhot.core.datastore.KairosDatastore;
import com.max256.abhot.core.exception.DatastoreException;
import com.max256.abhot.core.reporting.KairosMetricReporter;
import com.max256.abhot.util.Tags;
import com.max256.abhot.util.Util;
import com.max256.abhot.util.ValidationException;
import com.max256.abhot.util.Validator;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.max256.abhot.util.Preconditions.checkNotNullOrEmpty;

public class PutMillisecondCommand implements TelnetCommand, KairosMetricReporter
{
	private KairosDatastore m_datastore;
	private AtomicInteger m_counter = new AtomicInteger();
	private String m_hostName;
	private LongDataPointFactory m_longFactory;
	private DoubleDataPointFactory m_doubleFactory;

	@Inject
	public PutMillisecondCommand(KairosDatastore datastore, @Named("HOSTNAME") String hostname,
			LongDataPointFactory longFactory, DoubleDataPointFactory doubleFactory)
	{
		checkNotNullOrEmpty(hostname);
		m_hostName = hostname;
		m_datastore = datastore;
		m_longFactory = longFactory;
		m_doubleFactory = doubleFactory;
	}

	@Override
	public void execute(Channel chan, String[] command) throws DatastoreException, ValidationException
	{
		long timestamp = Util.parseLong(command[2]);
		execute(command, timestamp);
	}

	protected void execute(String[] command, long timestamp) throws ValidationException, DatastoreException
	{
		Validator.validateNotNullOrEmpty("metricName", command[1]);

		String metricName = command[1];
		int ttl = 0;

		DataPoint dp;
		try
		{
			if (command[3].contains("."))
				dp = m_doubleFactory.createDataPoint(timestamp, Double.parseDouble(command[3]));
			else
				dp = m_longFactory.createDataPoint(timestamp, Util.parseLong(command[3]));
		}
		catch (NumberFormatException e)
		{
			throw new ValidationException(e.getMessage());
		}

		ImmutableSortedMap.Builder<String, String> tags = Tags.create();

		int tagCount = 0;
		for (int i = 4; i < command.length; i++)
		{
			String[] tag = command[i].split("=");
			validateTag(tagCount, tag);

			if ("kairos_opt.ttl".equals(tag[0]))
			{
				try
				{
					ttl = Integer.parseInt(tag[1]);
				}
				catch (NumberFormatException nfe)
				{
					throw new ValidationException("tag[kairos_opt.ttl] must be a number");
				}

			}
			else
			{
				tags.put(tag[0], tag[1]);
				tagCount++;
			}
		}

		if (tagCount == 0)
			tags.put("add", "tag");

		m_counter.incrementAndGet();
		m_datastore.putDataPoint(metricName, tags.build(), dp, ttl);
	}

	private void validateTag(int tagCount, String[] tag) throws ValidationException
	{
		if (tag.length < 2)
			throw new ValidationException(String.format("tag[%d] must be in the format 'name=value'.", tagCount));

		Validator.validateNotNullOrEmpty(String.format("tag[%d].name", tagCount), tag[0]);

		Validator.validateNotNullOrEmpty(String.format("tag[%d].value", tagCount), tag[1]);
	}

	@Override
	public String getCommand()
	{
		return ("putm");
	}

	@Override
	public List<DataPointSet> getMetrics(long now)
	{
		DataPointSet dps = new DataPointSet(REPORTING_METRIC_NAME);
		dps.addTag("host", m_hostName);
		dps.addTag("method", "putm");
		dps.addDataPoint(m_longFactory.createDataPoint(now, m_counter.getAndSet(0)));

		return (Collections.singletonList(dps));
	}
}
