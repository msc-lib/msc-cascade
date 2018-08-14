package fun.pplm.msc.cascade.utils;

import java.sql.Timestamp;

public final class Constant {
	public static final SystemInfo SYSTEM_INFO = new SystemInfo();
	
	static class SystemInfo {
		public String name = "msc cascade";
		public String version = "1.0.0 20180814";
		public String startup = new Timestamp(System.currentTimeMillis()).toString();

		public SystemInfo() {
			super();
		}
	}
	
}
