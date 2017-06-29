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

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jboss.netty.channel.Channel;
import com.max256.abhot.core.datapoints.DoubleDataPointFactory;
import com.max256.abhot.core.datapoints.LongDataPointFactory;
import com.max256.abhot.core.datastore.KairosDatastore;
import com.max256.abhot.core.exception.DatastoreException;
import com.max256.abhot.util.Util;
import com.max256.abhot.util.ValidationException;

public class PutCommand extends PutMillisecondCommand
{
	@Inject
	public PutCommand(KairosDatastore datastore, @Named("HOSTNAME") String hostname,
			LongDataPointFactory longFactory, DoubleDataPointFactory doubleFactory)
	{
		super(datastore, hostname, longFactory, doubleFactory);
	}

	@Override
	public void execute(Channel chan, String[] command) throws DatastoreException, ValidationException
	{
		long timestamp = Util.parseLong(command[2]);
		//Backwards compatible hack for the next 30 years
		//This allows clients to send seconds to us
		if (timestamp < 3000000000L)
			timestamp *= 1000;

		execute(command, timestamp);
	}

	@Override
	public String getCommand()
	{
		return ("put");
	}

}