package fun.pplm.msc.cascade;

import fun.pplm.msc.framework.vertx.VerticleRunner;

public class Main {
	
	public static void main(String[] args) {
		VerticleRunner.startup(CascadeVerticle.class);
	}
}
