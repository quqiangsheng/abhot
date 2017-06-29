package com.max256.abhot.rollup;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.max256.abhot.core.http.rest.RollUpResource;

public class RollUpModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(RollUpResource.class).in(Scopes.SINGLETON);
		bind(RollUpManager.class).in(Scopes.SINGLETON);
		bind(RollUpTasksStore.class).to(RollUpTasksFileStore.class).in(Scopes.SINGLETON);
		bind(RollUpJob.class);
		bindConstant().annotatedWith(Names.named("STORE_DIRECTORY")).to("/tmp");
	}
}
