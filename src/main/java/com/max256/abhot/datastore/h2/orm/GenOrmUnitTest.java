package com.max256.abhot.datastore.h2.orm;

public class GenOrmUnitTest
	{
	public static void performUnitTests()
		{
		Metric.factory.testQueryMethods();
		Tag.factory.testQueryMethods();
		DataPoint.factory.testQueryMethods();
		MetricTag.factory.testQueryMethods();

		}
	}
