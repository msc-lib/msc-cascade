package fun.pplm.msc.cascade;

import java.util.function.Consumer;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Main {

	public static void startup() {
		VertxOptions options = new VertxOptions();
		options.setClustered(false);
		
		Consumer<Vertx> runner = vertx -> {
			try {
				vertx.deployVerticle(CascadeVerticle.class.getName());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		};
		Vertx vertx = Vertx.vertx(options);
		runner.accept(vertx);
	}
	
	public static void main(String[] args) {
		startup();
	}
}
