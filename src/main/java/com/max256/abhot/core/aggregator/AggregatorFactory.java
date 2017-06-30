package com.max256.abhot.core.aggregator;

import com.google.common.collect.ImmutableList;

public interface AggregatorFactory
{
	Aggregator createAggregator(String name);

    ImmutableList<AggregatorMetadata> getAggregatorMetadata();
}
