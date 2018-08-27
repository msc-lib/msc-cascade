package fun.pplm.msc.cascade;

import java.util.List;

import org.jboss.resteasy.plugins.server.vertx.VertxRegistry;

import fun.pplm.msc.cascade.service.AreaDataService;
import fun.pplm.msc.cascade.service.DebugService;
import fun.pplm.msc.cascade.service.InfoService;
import fun.pplm.msc.framework.vertx.ResteasyVerticle;
import fun.pplm.msc.framework.vertx.provider.ExceptionProvider;

public class CascadeVerticle extends ResteasyVerticle {

	@Override
	protected void registerProviders(List<Class<?>> classes) {
		classes.add(ExceptionProvider.class);
	}

	@Override
	protected void registerService(VertxRegistry registry) {
		registry.addPerInstanceResource(AreaDataService.class);
	    registry.addPerInstanceResource(InfoService.class);
	    registry.addPerInstanceResource(DebugService.class);
	}
	
}
