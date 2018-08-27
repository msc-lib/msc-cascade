package fun.pplm.msc.cascade.service;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import fun.pplm.msc.cascade.dao.AreaIview;
import fun.pplm.msc.cascade.query.IviewQuery;
import fun.pplm.msc.cascade.utils.Constant;
import fun.pplm.msc.framework.vertx.utils.ResHelper;

@Path("/areadata/iview/v1.0")
@Produces(MediaType.APPLICATION_JSON)
public class AreaDataService {

	private AreaIview areaIview = AreaIview.INST;

	@GET
	@Path("/areadata")
	public Object doGetAreaData() {
		return ResHelper.success(areaIview.getIviewData());
	}
	
	@GET
	@Path("/areadata/version")
	public Object doGetAreaDataVersion() {
		return ResHelper.success(Constant.DATA_VERSION_DATA);
	}

	@GET
	@Path("/province")
	public Object doGetProvince() {
		return ResHelper.success(areaIview.getProvinces());
	}

	@POST
	@Path("/province")
	@Consumes(MediaType.APPLICATION_JSON)
	public Object doPostProvince(IviewQuery iviewQuery) {
		if (iviewQuery == null) {
			return ResHelper.success(areaIview.getProvinces(null, false));
		}
		return ResHelper.success(areaIview.getProvinces(iviewQuery.getProvValues(), iviewQuery.getDeep() == 1 ? true : false));
	}
	
	@GET
	@Path("/city/{provValue}")
	public Object doGetCity(@PathParam("provValue") String provValue) {
		return ResHelper.success(areaIview.getCitiesByProinvceCode(provValue));
	}
	
	@GET
	@Path("/city/pinyin")
	public Object doGetCityPinyin() {
		return ResHelper.success(areaIview.getCityPinyin());
	}
	
	@GET
	@Path("/city/pinyin/version")
	public Object doGetCityPinyinVersion() {
		return ResHelper.success(Constant.DATA_VERSION_PINYIN);
	}
	
	@POST
	@Path("/city")
	@Consumes(MediaType.APPLICATION_JSON)
	public Object doPostCity(IviewQuery iviewQuery) {
		if (iviewQuery == null) {
			return ResHelper.success();
		}
		return ResHelper.success(areaIview.getCities(iviewQuery.getProvValues(), iviewQuery.getCityValues(),
				iviewQuery.getDeep() == 1 ? true : false));
	}
	
	@GET
	@Path("/area/{cityValue}")
	public Object doGetArea(@PathParam("cityValue") String cityValue) {
		return ResHelper.success(areaIview.getAreas(cityValue));
	}

	@POST
	@Path("/area")
	@Consumes(MediaType.APPLICATION_JSON)
	public Object doPostArea(IviewQuery iviewQuery) {
		if (iviewQuery == null) {
			return ResHelper.success();
		}
		return ResHelper.success(areaIview.getAreas(iviewQuery.getCityValues(), iviewQuery.getAreaValues()));
	}
	
	@POST
	@Path("/value")
	@Consumes(MediaType.APPLICATION_JSON)
	public Object doPostValue(List<String> values) {
		if (values == null || values.isEmpty()) {
			return ResHelper.success(Collections.emptyList());
		}
		return ResHelper.success(areaIview.getFromValue(values));
	}
	
	@GET
	@Path("/value/city")
	@Consumes(MediaType.APPLICATION_JSON)
	public Object doGetValueCity(@QueryParam("city") String city) {
		if (StringUtils.isBlank(city)) {
			return ResHelper.success(Collections.emptyList());
		}
		return ResHelper.success(areaIview.getCitiesByValue(city));
	}
	
}
