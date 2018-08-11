package fun.pplm.msc.cascade;

import org.jboss.resteasy.plugins.server.vertx.VertxRequestHandler;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;

import io.vertx.core.AbstractVerticle;

public class CascadeVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		VertxResteasyDeployment deployment = new VertxResteasyDeployment();
	    deployment.start();
	    deployment.getRegistry().addPerInstanceResource(AreaDataService.class);

	    // Start the front end server using the Jax-RS controller
	    vertx.createHttpServer()
	        .requestHandler(new VertxRequestHandler(vertx, deployment))
	        .listen(8080, ar -> {
	          System.out.println("Server started on port "+ ar.result().actualPort());
	        });

		
		/*
		Router router = Router.router(vertx);
		
		router.get("/areadata/iview/v1.0").produces("application/json").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.end(ResHelper.success(Area.INST.getAreaData()));
		});

		router.get("/areadata/iview/v1.0/areadata").produces("application/json").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.end(ResHelper.success(areaIview.getIviewData()));
		});

		router.get("/areadata/iview/v1.0/province").produces("application/json").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.end(ResHelper.success(areaIview.getProvinces()));
		});

		router.post("/areadata/iview/v1.0/province").consumes("application/json").produces("application/json").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			String body = routingContext.getBodyAsString();
			if (StringUtils.isEmpty(body)) {
				response.end(ResHelper.success(areaIview.getProvinces(null, false)));
				return;
			}
			IviewQuery iviewQuery = Json.decodeValue(body, IviewQuery.class);
			response.end(ResHelper.success(
					areaIview.getProvinces(iviewQuery.getProvValues(), iviewQuery.getDeep() == 1 ? true : false)));
		});

		router.get("/areadata/iview/v1.0/city/{provValue}").produces("application/json").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			String provValue = routingContext.pathParam("provValue");
			response.end(ResHelper.success(areaIview.getCities(provValue)));
		});

		router.post("/areadata/iview/v1.0/city").consumes("application/json").produces("application/json").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			String body = routingContext.getBodyAsString();
			System.out.println("11111111111111111");
			if (StringUtils.isEmpty(body)) {
				response.end(ResHelper.success());
				return;
			}
			System.out.println(body);
			IviewQuery iviewQuery = Json.decodeValue(body, IviewQuery.class);
			response.end(ResHelper.success(areaIview.getCities(iviewQuery.getProvValues(), iviewQuery.getCityValues(),
					iviewQuery.getDeep() == 1 ? true : false)));
		});

		router.get("/areadata/iview/v1.0/area/{cityValue}").produces("application/json").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			String cityValue = routingContext.pathParam("cityValue");
			response.end(ResHelper.success(areaIview.getAreas(cityValue)));
		});

		router.post("/areadata/iview/v1.0/area").consumes("application/json").produces("application/json").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			String body = routingContext.getBodyAsString();
			if (StringUtils.isEmpty(body)) {
				response.end(ResHelper.success());
				return;
			}
			IviewQuery iviewQuery = Json.decodeValue(body, IviewQuery.class);
			response.end(ResHelper.success(areaIview.getAreas(iviewQuery.getCityValues(), iviewQuery.getAreaValues())));
		});

		router.post("/areadata/iview/v1.0/value").produces("application/json").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			String body = routingContext.getBodyAsString();
			if (StringUtils.isEmpty(body)) {
				response.end(ResHelper.success(Collections.emptyList()));
				return;
			}
			List<String> values = Json.decodeValue(body, new TypeReference<List<String>>() {
			});
			response.end(ResHelper.success(areaIview.getFromValue(values)));
		});

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
		*/
	}
}
