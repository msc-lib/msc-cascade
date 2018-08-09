package fun.pplm.msc.cascade;

import java.util.function.Consumer;

import fun.pplm.msc.cascade.data.Area;
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
		String r = ResHelper.success(Area.INST.getAreaData(), true);
		System.out.println(r);
		System.out.println();
	}
}
