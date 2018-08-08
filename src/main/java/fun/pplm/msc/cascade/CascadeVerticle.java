package fun.pplm.msc.cascade;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class CascadeVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
    	 Router router = Router.router(vertx);
    	 router.route().produces("application/json").handler(BodyHandler.create());
    	 
    	 router.get("/area/iview").handler(routingContext -> {
    		 HttpServerResponse response = routingContext.response();
    		 response.end(ResHelper.success(AreaData.AREA_DATA.getAreaData()));
    	 });

         vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
