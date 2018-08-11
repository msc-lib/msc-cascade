package fun.pplm.msc.cascade;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import fun.pplm.msc.cascade.data.AreaIview;
import io.vertx.core.json.Json;

@Path("/areadata/iview/v1.0")
@Produces(MediaType.APPLICATION_JSON)
public class AreaDataService {

	private AreaIview areaIview = AreaIview.INST;

	@GET
	@Path("/areadata")
	public Response doGetAreaData() {
		return ResHelper.success(areaIview.getIviewData());
	}

	@GET
	@Path("/province")
	public Response doGetProvince() {
		return ResHelper.success(areaIview.getProvinces());
	}

	@POST
	@Path("/province")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPostProvince(String body) {
		if (StringUtils.isBlank(body)) {
			return ResHelper.success(areaIview.getProvinces(null, false));
		}
		IviewQuery iviewQuery = Json.decodeValue(body, IviewQuery.class);
		System.out.println(Json.encodePrettily(areaIview.getProvinces(iviewQuery.getProvValues(), iviewQuery.getDeep() == 1 ? true : false)));
		return ResHelper.success(areaIview.getProvinces(iviewQuery.getProvValues(), iviewQuery.getDeep() == 1 ? true : false));
	}
	
	@GET
	@Path("/city/{provValue}")
	public Response doGetCity(@PathParam("provValue") String provValue) {
		return ResHelper.success(areaIview.getCities(provValue));
	}
	
	@POST
	@Path("/city")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPostCity(String body) {
		if (StringUtils.isBlank(body)) {
			return ResHelper.success();
		}
		IviewQuery iviewQuery = Json.decodeValue(body, IviewQuery.class);
		return ResHelper.success(areaIview.getCities(iviewQuery.getProvValues(), iviewQuery.getCityValues(),
				iviewQuery.getDeep() == 1 ? true : false));
	}
	
	@GET
	@Path("/area/{cityValue}")
	public Response doGetArea(@PathParam("cityValue") String cityValue) {
		return ResHelper.success(areaIview.getAreas(cityValue));
	}

	@POST
	@Path("/area")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPostArea(String body) {
		if (StringUtils.isBlank(body)) {
			return ResHelper.success();
		}
		IviewQuery iviewQuery = Json.decodeValue(body, IviewQuery.class);
		return ResHelper.success(areaIview.getAreas(iviewQuery.getCityValues(), iviewQuery.getAreaValues()));
	}
	
	@POST
	@Path("/value")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPostValue(String body) {
		if (StringUtils.isBlank(body)) {
			return ResHelper.success(Collections.emptyList());
		}
		List<String> values = Json.decodeValue(body, new TypeReference<List<String>>(){});
		return ResHelper.success(areaIview.getFromValue(values));
	}
	
}