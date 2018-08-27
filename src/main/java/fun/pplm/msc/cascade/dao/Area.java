package fun.pplm.msc.cascade.dao;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import fun.pplm.msc.cascade.utils.Constant;
import fun.pplm.msc.cascade.utils.PrintHelper;

public class Area {

	public static final String XIA_LABEL = "市辖区";
	public static final String XIA_PREFIX = "(辖)";

	public static final String REGEX_CODE_PROVICE = "[1-9][0-9]0000";
	public static final String REGEX_CODE_CITY = "[1-9][0-9][0-9][1-9]00";
	public static final String REGEX_CODE_AREA = "[1-9][0-9][0-9][1-9][0-9][1-9]";
	
	public static final String REGEX_CITY_POSTFIX = "[市$|自治州$|地区$]";

	public static Area INST = new Area();

	/**
	 * 原始数据，编码索引行政区划名称
	 */
	private Map<String, String> areaData = new LinkedHashMap<>();

	/**
	 * 二级级联表达行政区划的数据关系，编码索引行政区划
	 */
	private Map<String, Map<String, String>> mapData = new LinkedHashMap<>();

	/**
	 * 行政区划中文名称索引编码
	 */
	private Map<String, String> valueData = new HashMap<>();

	private Area() {
		super();
		init();
	}

	private void init() {
		loadAreaData();
		processAreaData();
	}

	private void loadAreaData() {
		URL url = ClassLoader.getSystemResource("area.data");

		if (url != null) {
			try {
				List<String> lines = IOUtils.readLines(ClassLoader.getSystemResourceAsStream("area.data"), "utf-8");
				lines.stream().filter(StringUtils::isNoneBlank)
					.map(line -> line.split(","))
					.filter(cols -> cols.length == 2 && StringUtils.isNotBlank(cols[0]) && StringUtils.isNotBlank(cols[1]))
					.filter(cols -> Constant.SPECIAL_AREA || cols[0].matches("[1-6].*"))
					.forEach(cols -> areaData.put(cols[0], cols[1]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void processAreaData() {
		String temp = null;
		for (Entry<String, String> entry : this.areaData.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (temp == null) {
				temp = key;
			} else {
				/**
				 * 处理直辖市
				 */
				if (temp.matches("[1-6][0-6]0000")) {
					if (!key.matches("[1-6][0-6]0100")) {
						String fixedKey = temp.substring(0, 2) + "0100";
						add(fixedKey, XIA_LABEL);
						// 当只处理城市的市辖区时，需要转变成对应的直辖市名称，而不是使用市辖区。
						valueData.put(XIA_PREFIX + this.areaData.get(temp), fixedKey);
					}
				}
			}
			temp = key;
			add(key, value);
			valueData.put(value, key);
		}
		this.areaData.clear();
	}

	private void add(String key, String value) {
		if (key.matches(REGEX_CODE_PROVICE)) {
			add("86", key, value);
		} else if (key.matches(REGEX_CODE_CITY)) {
			add(key.substring(0, 2) + "0000", key, value);
		} else if (key.matches(REGEX_CODE_AREA)) {
			add(key.substring(0, 4) + "00", key, value);
		}
	}

	private void add(String parentKey, String key, String value) {
		if (mapData.containsKey(parentKey)) {
			mapData.get(parentKey).put(key, value);
		} else {
			Map<String, String> map = new LinkedHashMap<>();
			map.put(key, value);
			mapData.put(parentKey, map);
		}
	}

	public Map<String, String> getAreaData() {
		if (areaData.isEmpty()) {
			loadAreaData();
		}
		return areaData;
	}

	public Map<String, Map<String, String>> getMapData() {
		return mapData;
	}

	public Map<String, String> getValueData() {
		return valueData;
	}

	public static void writeAreaJson() {
		URL baseUrl = ClassLoader.getSystemResource("");
		File file = new File(baseUrl.getFile(), "area.json");
		ObjectMapper objectMapper = new ObjectMapper();
		String content;
		try {
			content = objectMapper.writeValueAsString(Area.INST.getMapData());
			System.out.println(content);
			FileUtils.writeStringToFile(file, content, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// writeAreaJson();
		PrintHelper.jsonPretty(Area.INST.valueData);
		PrintHelper.jsonPretty(Area.INST.valueData.get(XIA_PREFIX + "北京" + "市"));
		PrintHelper.jsonPretty("阿坝藏族羌族自治州".replaceAll(REGEX_CITY_POSTFIX, ""));
	}
}
