package fun.pplm.msc.cascade.utils;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertx.core.json.Json;

public final class ResHelper {
	
	public static Response success() {
		return success(null);
	}
	
	public static Response success(Object content) {
		String resMsg = Json.encode(new ResBean(content));
		return Response.ok(resMsg).build();
	}
	
	public static class ResBean {
		private String code = "0";
		private String message = "success";
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
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
	
}
