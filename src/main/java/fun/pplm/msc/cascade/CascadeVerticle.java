package fun.pplm.msc.cascade;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.resteasy.plugins.server.vertx.VertxRegistry;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;

import fun.pplm.msc.cascade.service.AreaDataService;
import fun.pplm.msc.cascade.service.InfoService;
import fun.pplm.msc.framework.vertx.ResteasyVerticle;
import fun.pplm.msc.framework.vertx.provider.ExceptionProvider;

public class CascadeVerticle extends ResteasyVerticle {

	@Override
	protected void deploy(VertxResteasyDeployment deployment) {
		deployment.setActualProviderClasses(Stream.of(ExceptionProvider.class).collect(Collectors.toList()));
		
	}

	@Override
	protected void register(VertxRegistry registry) {
		registry.addPerInstanceResource(AreaDataService.class);
	    registry.addPerInstanceResource(InfoService.class);
		
	}
	
}
