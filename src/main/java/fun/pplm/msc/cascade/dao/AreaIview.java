package fun.pplm.msc.cascade.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import fun.pplm.msc.cascade.utils.PyHelper;
import fun.pplm.msc.framework.vertx.utils.PrintHelper;

public class AreaIview {

	public static AreaIview INST = new AreaIview();

	/**
	 * @see(Area.mapData)
	 */
	private Map<String, Map<String, String>> mapData;

	/**
	 * @see(Area.valueData)
	 */
	private Map<String, String> valueData;

	/**
	 * 行政区划编码索引行政区划对象，数据来源@see(Area.mapData)
	 */
	private Map<String, IviewBean> iviewMapData = new HashMap<>();

	/**
	 * 数据来源@see(Area.mapData)
	 */
	private IviewBean iviewData = new IviewBean("86", "中国");

	/**
	 * 有且仅有城市信息的集合, 数据来源@see(Area.valueData)
	 */
	private List<IviewBean> cityData = new ArrayList<>();

	/**
	 * 带拼音的有且仅有城市信息的集合, 数据来源@see(Area.valueData)
	 */
	private List<IviewBean> cityPinyinData = new ArrayList<>();

	private AreaIview() {
		super();
		init();
	}

	private void init() {
		this.mapData = Area.INST.getMapData();
		this.valueData = Area.INST.getValueData();
		processIviewData(iviewData.getValue(), iviewData);
		processCityData();
		processCityPinyin();
	}

	private void processIviewData(String key, IviewBean iviewBean) {
		iviewMapData.put(key, iviewBean);
		if (mapData.containsKey(key)) {
			List<IviewBean> children = new ArrayList<>();
			iviewBean.setChildren(children);
			for (Entry<String, String> entry : mapData.get(key).entrySet()) {
				String keyTemp = entry.getKey();
				IviewBean temp = new IviewBean(keyTemp, entry.getValue());
				children.add(temp);
				if (mapData.containsKey(keyTemp)) {
					processIviewData(keyTemp, temp);
				}
			}
		}
	}

	private void processCityData() {
		for (Entry<String, String> item : valueData.entrySet()) {
			String key = item.getKey();
			if (item.getValue().matches(Area.REGEX_CODE_CITY)) {
				key = key.replace(Area.XIA_PREFIX, "");
				IviewBean iviewBean = new IviewBean(item.getValue(), key);
				this.cityData.add(iviewBean);
			}
		}
	}

	private void processCityPinyin() {
		this.cityPinyinData = this.cityData.stream().map(item -> {
			IviewBean iviewBean = item.clone();
			iviewBean.pinyin = PyHelper.getPingYin(iviewBean.label.replaceAll(Area.REGEX_CITY_POSTFIX, ""));
			return iviewBean;
		}).sorted((e1, e2) -> e1.pinyin.compareTo(e2.pinyin)).collect(Collectors.toList());
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

	public List<IviewBean> getCitiesByProinvceCode(String provinceCode) {
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

	/**
	 * 通过行政区划名称获取行政区划信息（单层） 匹配完整行政区划名称，可省略（省、市、区、县）单位 返回level@see(IviewBean.level)
	 * 
	 * @param values
	 * @return
	 */
	public List<IviewBean> getFromValue(List<String> values) {
		final List<IviewBean> iviews = new ArrayList<>();
		for (String value : values) {
			iviews.addAll(getQueryKey(value).stream().filter(key -> valueData.containsKey(key))
					.map(key -> new IviewBean(valueData.get(key), key, true)).collect(Collectors.toList()));
		}
		return iviews;
	}

	private List<String> getQueryKey(String value) {
		return Arrays.<String>asList(value, value + "省", value + "市", value + "区", value + "县", value + "州",
				Area.XIA_PREFIX + value, Area.XIA_PREFIX + value + "市");
	}

	public List<IviewBean> getIviewData() {
		return iviewData.getChildren();
	}

	public List<IviewBean> getCitiesByValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			return cityData.stream().filter(item -> {
				int index = item.getLabel().indexOf(value);
				return index != -1 && index != item.label.length() - 1;
			}).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	public List<IviewBean> getCityPinyin() {
		return cityPinyinData;
	}

	public static class IviewBean implements Cloneable {

		public static final String LEVEL_PROVINCE = "1";
		public static final String LEVEL_CITY = "2";
		public static final String LEVEL_AREA = "3";

		private String value;
		private String label;

		/**
		 * 1：省（直辖市，自治区，特别行政区），2：市（州），3：区（县）
		 */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private String level;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		private String pinyin;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		private List<IviewBean> children;

		public IviewBean() {
			super();
		}

		public IviewBean(String value, String label) {
			this(value, label, false);
		}

		public IviewBean(String value, String label, boolean levelFlag) {
			super();
			this.value = value;
			this.label = label;
			if (levelFlag) {
				initLevel();
			}
		}

		public void initLevel() {
			if (StringUtils.isNoneBlank(value)) {
				if (value.matches(Area.REGEX_CODE_PROVICE)) {
					this.level = LEVEL_PROVINCE;
				} else if (value.matches(Area.REGEX_CODE_CITY)) {
					this.level = LEVEL_CITY;
				} else if (value.matches(Area.REGEX_CODE_AREA)) {
					this.level = LEVEL_AREA;
				}
			}
		}

		@Override
		public IviewBean clone() {
			return new IviewBean(value, label);
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

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
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
		//PrintHelper.jsonPretty(AreaIview.INST.getCityPinyin());
		PrintHelper.jsonPretty(AreaIview.INST.getFromValue(Stream.of("北京").collect(Collectors.toList())));
	}
}
