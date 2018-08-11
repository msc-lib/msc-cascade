package fun.pplm.msc.cascade.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AreaIview {

	public static AreaIview INST = new AreaIview();

	private Map<String, Map<String, String>> mapData;

	private Map<String, String> valueData;

	private Map<String, IviewBean> iviewMapData = new HashMap<>();

	private IviewBean iviewData = new IviewBean("86", "中国");

	private AreaIview() {
		super();
		init();
	}

	private void init() {
		this.mapData = Area.INST.getMapData();
		this.valueData = Area.INST.getValueData();
		processIviewData();
	}

	private void processIviewData() {
		processIview(iviewData.getValue(), iviewData);
	}

	private void processIview(String key, IviewBean iviewBean) {
		iviewMapData.put(key, iviewBean);
		if (mapData.containsKey(key)) {
			List<IviewBean> children = new ArrayList<>();
			iviewBean.setChildren(children);
			for (Entry<String, String> entry : mapData.get(key).entrySet()) {
				String keyTemp = entry.getKey();
				IviewBean temp = new IviewBean(keyTemp, entry.getValue());
				children.add(temp);
				if (mapData.containsKey(keyTemp)) {
					processIview(keyTemp, temp);
				}
			}
			;
		}
	}
	
	public List<IviewBean> getProvinces() {
		return getProvinces(null, false);
	}

	public List<IviewBean> getProvinces(List<String> provinceCodes, boolean deep) {
		List<IviewBean> data = iviewData.getChildren();
		if (provinceCodes != null && !provinceCodes.isEmpty()) {
			data = data.stream().filter(iview -> provinceCodes.contains(iview.getValue())).collect(Collectors.toList());
		}
		if (!deep) {
			data = data.stream().map(iview -> new IviewBean(iview.value, iview.label)).collect(Collectors.toList());
		}
		return data;
	}

	public List<IviewBean> getCities(String provinceCode) {
		return getCities(Stream.of(provinceCode).collect(Collectors.toList()), null, false);
	}
	
	public List<IviewBean> getCities(List<String> provinceCodes, List<String> cityCodes, boolean deep) {
		List<IviewBean> data = Collections.emptyList();
		if (cityCodes != null && !cityCodes.isEmpty()) {
			data = cityCodes.stream().filter(code -> iviewMapData.containsKey(code)).map(code -> iviewMapData.get(code))
					.collect(Collectors.toList());
		} else if (provinceCodes != null && !provinceCodes.isEmpty()) {
			data = new ArrayList<>();
			provinceCodes.stream().filter(code -> iviewMapData.containsKey(code))
					.map(code -> iviewMapData.get(code).getChildren()).reduce(data, (r1, r2) -> {
						r1.addAll(r2);
						return r1;
					});
		}
		if (!deep) {
			data = data.stream().map(iview -> new IviewBean(iview.value, iview.label)).collect(Collectors.toList());
		}
		return data;
	}

	public List<IviewBean> getAreas(String cityCode) {
		return getAreas(Stream.of(cityCode).collect(Collectors.toList()), null);
	}
	
	public List<IviewBean> getAreas(List<String> cityCodes, List<String> areaCodes) {
		List<IviewBean> data = Collections.emptyList();
		if (areaCodes != null && !areaCodes.isEmpty()) {
			data = areaCodes.stream().filter(code -> iviewMapData.containsKey(code)).map(code -> iviewMapData.get(code))
					.collect(Collectors.toList());
		} else if (cityCodes != null && !cityCodes.isEmpty()) {
			data = new ArrayList<>();
			cityCodes.stream().filter(code -> iviewMapData.containsKey(code))
					.map(code -> iviewMapData.get(code).getChildren()).reduce(data, (r1, r2) -> {
						r1.addAll(r2);
						return r1;
					});
		}
		return data;
	}

	public List<IviewBean> getIviewData() {
		return iviewData.getChildren();
	}

	public List<IviewBean> getFromValue(List<String> values) {
		return values.stream().filter(value -> valueData.containsKey(value) || valueData.containsKey(value + "省") || valueData.containsKey(value + "市")
				|| valueData.containsValue(value + "区") || valueData.containsValue(value + "县")).map(value -> {
					if (valueData.containsKey(value)) {
						return value;
					} else if (valueData.containsKey(value + "省")) {
						return value + "省";
					} else if (valueData.containsKey(value + "市")) {
						return value + "市";
					} else if (valueData.containsKey(value + "区")) {
						return value + "区";
					} else if (valueData.containsKey(value + "县")) {
						return value + "县";
					}
					return null;
				}).map(value -> new IviewBean(valueData.get(value), value)).collect(Collectors.toList());
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

}
