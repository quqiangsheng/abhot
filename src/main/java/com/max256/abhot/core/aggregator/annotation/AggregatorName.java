package com.max256.abhot.core.aggregator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AggregatorName
{
	String name();
	String description();
    AggregatorProperty[] properties() default {};
}
