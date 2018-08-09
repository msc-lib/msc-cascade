package fun.pplm.msc.cascade.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Area {
	
	public static Area INST = new Area();
	
	private Map<String, String> areaData = new LinkedHashMap<>();

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
				List<String> lines = FileUtils.readLines(new File(url.getFile()), "utf-8");
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
		Map<String, String> data = new LinkedHashMap<>();
		for (Entry<String, String> entry : this.areaData.entrySet()) {
			String key = entry.getKey();
			if (temp == null) {
				temp = key;
			} else {
				if (temp.matches("[1-6][0-6]0000")) {
					if (!key.matches("[1-6][0-6]0100")) {
						data.put(temp.substring(0, 2) + "0100", "ÊÐÏ½Çø");
					}
				}
			}
			temp = key;
			data.put(key, entry.getValue());
		}
		this.areaData.clear();
		this.areaData = data;
	}
	
	public Map<String, String> getAreaData() {
		return areaData;
	}

}
