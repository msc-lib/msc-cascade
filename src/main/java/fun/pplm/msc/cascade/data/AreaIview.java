package fun.pplm.msc.cascade.data;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;

import io.vertx.core.json.Json;

public class AreaIview {
	
	public static AreaIview INST = new AreaIview();
	
	private List<IviewBean> data;

	private AreaIview() {
		super();
		init();
	}

	private void init() {
		URL url = ClassLoader.getSystemResource("area-iview.data");
		try {
			this.data = Json.mapper.readValue(url, new TypeReference<List<IviewBean>>(){});
		} catch (IOException e) {
			processAreaData(url);
			e.printStackTrace();
		}
	}
	
	private void processAreaData(URL url) {
		Map<String, String> areaData = Area.INST.getAreaData();
		
	}
	
	public List<IviewBean> getAreaIviewData() {
		return null;
	}
	
	public static class IviewBean {
		private String value;
		private String label;
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private String pinyin;
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private List<IviewBean> children;
		
		public IviewBean() {
			super();
		}

		public IviewBean(String value, String label) {
			super();
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getPinyin() {
			return pinyin;
		}

		public void setPinyin(String pinyin) {
			this.pinyin = pinyin;
		}

		public List<IviewBean> getChildren() {
			return children;
		}

		public void setChildren(List<IviewBean> children) {
			this.children = children;
		}
		
	} 
	
	public static void main(String[] args) {
		AreaIview.INST.getAreaIviewData();
	}
	
}
