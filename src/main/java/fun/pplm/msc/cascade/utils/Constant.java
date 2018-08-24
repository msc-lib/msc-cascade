package fun.pplm.msc.cascade.utils;

import java.sql.Timestamp;

public final class Constant {
	
	public static final Version DATA_VERSION_PINYIN = new Version("v1.0.0", "2018-08-24 10:59:00");

	public static final Version DATA_VERSION_DATA = new Version("v1.0.0", "2018-08-24 10:59:00");
	
	public static final SystemInfo SYSTEM_INFO = new SystemInfo();
	
	static class SystemInfo {
		public String name = "msc cascade";
		public String version = "1.1.0 20180824";
		public String startup = new Timestamp(System.currentTimeMillis()).toString();

		public SystemInfo() {
			super();
		}
	}
	
	
	public static class Version {
		public String version;
		public String timestamp;
		
		public Version() {
			super();
		}

		public Version(String version, String timestamp) {
			super();
			this.version = version;
			this.timestamp = timestamp;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		
	}
	
}
