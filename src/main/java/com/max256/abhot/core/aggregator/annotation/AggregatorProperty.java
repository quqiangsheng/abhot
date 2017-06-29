package com.max256.abhot.core.aggregator.annotation;

public @interface AggregatorProperty
{
    public String name();
    public String type();
    public String[] values() default {};
}
