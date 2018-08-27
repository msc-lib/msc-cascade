package fun.pplm.msc.cascade.utils;

import java.sql.Timestamp;

public final class Constant {
	
	/**
	 * 特殊区域，true增加香港，澳门，台湾，钓鱼岛
	 */
	public static final boolean SPECIAL_AREA = false;
	
	public static final Version DATA_VERSION_PINYIN = new Version("v1.1.1", "2018-08-24 19:59:00");

	public static final Version DATA_VERSION_DATA = new Version("v1.0.0", "2018-08-24 10:59:00");
	
	public static final SystemInfo SYSTEM_INFO = new SystemInfo();
	
	static class SystemInfo {
		public String name = "msc cascade";
		public String version = "1.1.2 20180827";
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
