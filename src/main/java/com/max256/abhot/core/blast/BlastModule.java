package com.max256.abhot.core.blast;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BlastModule extends AbstractModule
{
	public static final Logger logger = LoggerFactory.getLogger(BlastModule.class);

	@Override
	protected void configure()
	{
		logger.info("Configuring module BlastModule");

		bind(BlastServer.class).in(Singleton.class);
	}
}
