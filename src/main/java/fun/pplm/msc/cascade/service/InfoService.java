package fun.pplm.msc.cascade.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fun.pplm.msc.cascade.utils.Constant;
import fun.pplm.msc.framework.vertx.utils.ResHelper;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class InfoService {
	
	@GET
	@Path("/sysinfo")
	public Object doGetSysinfo() {
		return ResHelper.success(Constant.SYSTEM_INFO);
	}
	
}
