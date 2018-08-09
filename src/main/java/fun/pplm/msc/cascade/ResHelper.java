package fun.pplm.msc.cascade;

import io.vertx.core.json.Json;

public class ResHelper {
	public static class ResBean {
		private String code = "0";
		private String message = "success";
		private Object content;

		public ResBean() {
			super();
		}

		public ResBean(Object content) {
			super();
			this.content = content;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Object getContent() {
			return content;
		}

		public void setContent(Object content) {
			this.content = content;
		}
		
	}

	public static String success(Object content) {
		return success(content, false);
	}
	
	public static String success(Object content, boolean pretty) {
		if (pretty) {
			return Json.encodePrettily(content);
		}
		return Json.encode(new ResBean(content));
		
	}
}
