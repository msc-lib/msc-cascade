package fun.pplm.msc.cascade;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import io.vertx.core.json.Json;

public class AreaData {
	
	private Map<String, Map<String, String>> map = Collections.emptyMap();
	
	public static AreaData AREA_DATA = new AreaData();

	public AreaData() {
		super();
		init();
	}
	
	private void init() {
		URL url = ClassLoader.getSystemResource("area.json");
		try {
			map = Json.mapper.readValue(url, new TypeReference<Map<String, Map<String, String>>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void iviewFormat() {
		IviewBean iviewBean = new IviewBean();
	}
	
	public Map<String, Map<String, String>> getAreaData() {
		return map;
	}
	
}
