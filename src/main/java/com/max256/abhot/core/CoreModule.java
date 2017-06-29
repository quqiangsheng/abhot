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

package com.max256.abhot.core;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;

import com.google.common.net.InetAddresses;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.max256.abhot.core.aggregator.AggregatorFactory;
import com.max256.abhot.core.aggregator.AvgAggregator;
import com.max256.abhot.core.aggregator.CountAggregator;
import com.max256.abhot.core.aggregator.DataGapsMarkingAggregator;
import com.max256.abhot.core.aggregator.DiffAggregator;
import com.max256.abhot.core.aggregator.DivideAggregator;
import com.max256.abhot.core.aggregator.FilterAggregator;
import com.max256.abhot.core.aggregator.FirstAggregator;
import com.max256.abhot.core.aggregator.GuiceAggregatorFactory;
import com.max256.abhot.core.aggregator.LastAggregator;
import com.max256.abhot.core.aggregator.LeastSquaresAggregator;
import com.max256.abhot.core.aggregator.MaxAggregator;
import com.max256.abhot.core.aggregator.MinAggregator;
import com.max256.abhot.core.aggregator.PercentileAggregator;
import com.max256.abhot.core.aggregator.RateAggregator;
import com.max256.abhot.core.aggregator.SamplerAggregator;
import com.max256.abhot.core.aggregator.SaveAsAggregator;
import com.max256.abhot.core.aggregator.ScaleAggregator;
import com.max256.abhot.core.aggregator.SmaAggregator;
import com.max256.abhot.core.aggregator.StdAggregator;
import com.max256.abhot.core.aggregator.SumAggregator;
import com.max256.abhot.core.aggregator.TrimAggregator;
import com.max256.abhot.core.datapoints.DoubleDataPointFactory;
import com.max256.abhot.core.datapoints.DoubleDataPointFactoryImpl;
import com.max256.abhot.core.datapoints.LegacyDataPointFactory;
import com.max256.abhot.core.datapoints.LongDataPointFactory;
import com.max256.abhot.core.datapoints.LongDataPointFactoryImpl;
import com.max256.abhot.core.datapoints.NullDataPointFactory;
import com.max256.abhot.core.datapoints.StringDataPointFactory;
import com.max256.abhot.core.datastore.GuiceQueryPluginFactory;
import com.max256.abhot.core.datastore.KairosDatastore;
import com.max256.abhot.core.datastore.QueryPluginFactory;
import com.max256.abhot.core.datastore.QueryQueuingManager;
import com.max256.abhot.core.groupby.BinGroupBy;
import com.max256.abhot.core.groupby.GroupByFactory;
import com.max256.abhot.core.groupby.GuiceGroupByFactory;
import com.max256.abhot.core.groupby.TagGroupBy;
import com.max256.abhot.core.groupby.TimeGroupBy;
import com.max256.abhot.core.groupby.ValueGroupBy;
import com.max256.abhot.core.http.rest.json.QueryParser;
import com.max256.abhot.core.jobs.CacheFileCleaner;
import com.max256.abhot.core.scheduler.KairosDBScheduler;
import com.max256.abhot.core.scheduler.KairosDBSchedulerImpl;
import com.max256.abhot.util.MemoryMonitor;
import com.max256.abhot.util.Util;

public class CoreModule extends AbstractModule
{
	public static final String DATAPOINTS_FACTORY_LONG = "kairosdb.datapoints.factory.long";
	public static final String DATAPOINTS_FACTORY_DOUBLE = "kairosdb.datapoints.factory.double";
	private Properties m_props;

	public CoreModule(Properties props)
	{
		m_props = props;
	}

	@SuppressWarnings("rawtypes")
	private Class getClassForProperty(String property)
	{
		String className = m_props.getProperty(property);

		Class klass = null;
		try
		{
			klass = getClass().getClassLoader().loadClass(className);
		}
		catch (ClassNotFoundException e)
		{
			throw new MissingResourceException("Unable to load class", className, property);
		}

		return (klass);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void configure()
	{
		bind(QueryQueuingManager.class).in(Singleton.class);
		bind(KairosDatastore.class).in(Singleton.class);
		bind(AggregatorFactory.class).to(GuiceAggregatorFactory.class).in(Singleton.class);
		bind(GroupByFactory.class).to(GuiceGroupByFactory.class).in(Singleton.class);
		bind(QueryPluginFactory.class).to(GuiceQueryPluginFactory.class).in(Singleton.class);
		bind(QueryParser.class).in(Singleton.class);
		bind(CacheFileCleaner.class).in(Singleton.class);
		bind(KairosDBScheduler.class).to(KairosDBSchedulerImpl.class).in(Singleton.class);
		bind(KairosDBSchedulerImpl.class).in(Singleton.class);
		bind(MemoryMonitor.class).in(Singleton.class);

		bind(SumAggregator.class);
		bind(MinAggregator.class);
		bind(MaxAggregator.class);
		bind(AvgAggregator.class);
		bind(StdAggregator.class);
		bind(RateAggregator.class);
		bind(SamplerAggregator.class);
		bind(LeastSquaresAggregator.class);
		bind(PercentileAggregator.class);
		bind(DivideAggregator.class);
		bind(ScaleAggregator.class);
		bind(CountAggregator.class);
		bind(DiffAggregator.class);
		bind(DataGapsMarkingAggregator.class);
		bind(FirstAggregator.class);
		bind(LastAggregator.class);
		bind(SaveAsAggregator.class);
		bind(TrimAggregator.class);
		bind(SmaAggregator.class);
		bind(FilterAggregator.class);

		bind(ValueGroupBy.class);
		bind(TimeGroupBy.class);
		bind(TagGroupBy.class);
		bind(BinGroupBy.class);

		Names.bindProperties(binder(), m_props);
		bind(Properties.class).toInstance(m_props);

		String hostname = m_props.getProperty("kairosdb.hostname");
		bindConstant().annotatedWith(Names.named("HOSTNAME")).to(hostname != null ? hostname: Util.getHostName());

		bind(new TypeLiteral<List<DataPointListener>>()
		{
		}).toProvider(DataPointListenerProvider.class);

		//bind datapoint default impls
		bind(DoubleDataPointFactory.class)
				.to(getClassForProperty(DATAPOINTS_FACTORY_DOUBLE)).in(Singleton.class);
		//This is required in case someone overwrites our factory property
		bind(DoubleDataPointFactoryImpl.class).in(Singleton.class);

		bind(LongDataPointFactory.class)
				.to(getClassForProperty(DATAPOINTS_FACTORY_LONG)).in(Singleton.class);
		//This is required in case someone overwrites our factory property
		bind(LongDataPointFactoryImpl.class).in(Singleton.class);

		bind(LegacyDataPointFactory.class).in(Singleton.class);

		bind(StringDataPointFactory.class).in(Singleton.class);

		bind(StringDataPointFactory.class).in(Singleton.class);

		bind(NullDataPointFactory.class).in(Singleton.class);

		bind(KairosDataPointFactory.class).to(GuiceKairosDataPointFactory.class).in(Singleton.class);

		String hostIp = m_props.getProperty("kairosdb.host_ip");
		bindConstant().annotatedWith(Names.named("HOST_IP")).to(hostIp != null ? hostIp: InetAddresses.toAddrString(Util.findPublicIp()));
	}
}
