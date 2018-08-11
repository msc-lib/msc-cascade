package fun.pplm.msc.cascade.data;

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

public class Area {
	
	public static Area INST = new Area();
	
	private Map<String, String> areaData = new LinkedHashMap<>();
	
	private Map<String, Map<String, String>> mapData = new LinkedHashMap<>();
	
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
				lines.forEach(line -> {
					String[] items = line.split(",");
					if (items.length >= 2) {
						String key = items[0].trim();
						String value = items[1].trim();
						if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
							areaData.put(key, value);
						}
					}
				});
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
				if (temp.matches("[1-6][0-6]0000")) {
					if (!key.matches("[1-6][0-6]0100")) {
						add(temp.substring(0, 2) + "0100", "市辖区");
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
		if (key.matches("[1-9][0-9]0000")) {
			add("86", key, value);
		} else if (key.matches("[1-9][0-9][0-9][0-9]00")) {
			add(key.substring(0, 2) + "0000", key, value);
		} else {
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
		writeAreaJson();
	}
}
