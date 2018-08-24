package fun.pplm.msc.cascade.service;

import java.time.LocalDateTime;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fun.pplm.msc.cascade.utils.Constant;
import fun.pplm.msc.cascade.utils.Constant.Version;
import fun.pplm.msc.framework.vertx.utils.ResHelper;
import io.vertx.core.json.Json;

@Path("/debug")
@Produces(MediaType.APPLICATION_JSON)
public class DebugService {
	
	@POST
	@Path("/areadata/version")
	public Response doPostAreaDataVersion(String body) {
		Version version = Json.decodeValue(body, Constant.Version.class);
		Constant.DATA_VERSION_DATA.version = version.version;
		Constant.DATA_VERSION_DATA.timestamp = LocalDateTime.now().toString();
		return ResHelper.success(Constant.DATA_VERSION_DATA);
	}
	
	@POST
	@Path("/city/pinyin/version")
	public Response doPostCityPinyinVersion(String body) {
		Version version = Json.decodeValue(body, Constant.Version.class);
		Constant.DATA_VERSION_PINYIN.version = version.version;
		Constant.DATA_VERSION_PINYIN.timestamp = LocalDateTime.now().toString();
		return ResHelper.success(Constant.DATA_VERSION_PINYIN);
	}
	
}
