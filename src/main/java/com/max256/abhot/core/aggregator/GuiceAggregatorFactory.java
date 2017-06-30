package com.max256.abhot.core.aggregator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.inject.Binding;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.max256.abhot.core.aggregator.annotation.AggregatorName;
import com.max256.abhot.core.aggregator.annotation.AggregatorProperty;

import java.util.*;

public class GuiceAggregatorFactory implements AggregatorFactory
{
	private Map<String, Class<Aggregator>> m_aggregators = new HashMap<>();
    private List<AggregatorMetadata> m_aggregatorsMetadata = new ArrayList<>();
	private Injector m_injector;


	@Inject
	@SuppressWarnings("unchecked")
	public GuiceAggregatorFactory(Injector injector)
	{
		m_injector = injector;
		Map<Key<?>, Binding<?>> bindings = injector.getAllBindings();

        for (Key<?> key : bindings.keySet())
		{
			Class<?> bindingClass = key.getTypeLiteral().getRawType();
			if (Aggregator.class.isAssignableFrom(bindingClass))
			{
				AggregatorName ann = bindingClass.getAnnotation(AggregatorName.class);
				if (ann == null)
					throw new IllegalStateException("Aggregator class " + bindingClass.getName()+
							" does not have required annotation " + AggregatorName.class.getName());

				m_aggregators.put(ann.name(), (Class<Aggregator>)bindingClass);

                ImmutableList<AggregatorPropertyMetadata> properties = getAggregatorPropertyMetadata(ann);
                m_aggregatorsMetadata.add(new AggregatorMetadata(ann.name(), ann.description(), properties));
			}
		}
		Collections.sort(m_aggregatorsMetadata, new Comparator<AggregatorMetadata>()
		{
			@Override
			public int compare(AggregatorMetadata o1, AggregatorMetadata o2)
			{
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

    private ImmutableList<AggregatorPropertyMetadata> getAggregatorPropertyMetadata(AggregatorName ann)
    {
        Builder<AggregatorPropertyMetadata> builder = new Builder<>();
        for (AggregatorProperty aggregatorProperty : ann.properties()) {
            builder.add(new AggregatorPropertyMetadata(aggregatorProperty.name(), aggregatorProperty.type(), aggregatorProperty.values()));
        } return builder.build();
    }

    public Aggregator createAggregator(String name)
	{
		Class<Aggregator> aggClass = m_aggregators.get(name);

		if (aggClass == null)
			return (null);

        return (m_injector.getInstance(aggClass));
	}

    @Override
    public ImmutableList<AggregatorMetadata> getAggregatorMetadata()
    {
        return new Builder<AggregatorMetadata>().addAll(m_aggregatorsMetadata).build();
    }

}
