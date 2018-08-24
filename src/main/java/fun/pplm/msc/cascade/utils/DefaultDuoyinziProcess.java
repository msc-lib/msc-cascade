package fun.pplm.msc.cascade.utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class DefaultDuoyinziProcess implements DuoyinziProcess {

	private Map<String, Set<String>> duoyinziLib = new HashMap<>();

	public DefaultDuoyinziProcess() {
		super();
		init();
	}

	private void init() {
		loadDuoyinziLib();
	}

	private void loadDuoyinziLib() {
		URL url = ClassLoader.getSystemResource("duoyinzi.lib");
		if (url != null) {
			try {
				List<String> lines = IOUtils.readLines(ClassLoader.getSystemResourceAsStream("duoyinzi.lib"), "utf-8");
				lines.stream().filter(StringUtils::isNoneBlank)
					.map(line -> line.split("#"))
					.filter(cols -> cols.length == 2 && StringUtils.isNotBlank(cols[0]) && StringUtils.isNotBlank(cols[1]))
					.forEach(cols -> duoyinziLib.put(cols[0], Stream.of(cols[1].split(",")).filter(StringUtils::isNoneBlank).collect(Collectors.toSet())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String process(String hanyu, char zi, String[] pinyin) {
		if (pinyin == null || pinyin.length == 0) {
			return null;
		}
		if (pinyin.length == 1) {
			return pinyin[0];
		}
		List<String> words = wordProcess(hanyu, zi, 1, 1);
		Set<String> set = null;
		for (String yin : pinyin) {
			if (duoyinziLib.containsKey(yin)) {
				set = duoyinziLib.get(yin);
				for (String word : words) {
					if (set.contains(word)) {
						return yin;
					}
				}
			}
		}
		return pinyin[0];
	}

	/**
	 * 以字为中心（左侧第一个开始）进行分词，获取字的left right两端的各种组合 举例：大家用过都说好, 过, 1, 1; 结果：用过，过都，用过都
	 * 举例：大家用过都说好, 过, 0, 1; 结果：过都 举例：大家用过都说好, 过, 2, 0; 结果：家用过，用过 举例：大家用过都说好, 过, 2,
	 * 1; 结果：家用过都，家用过，用过，用过都， 过都
	 * 
	 * @param hanyu
	 * @param zi
	 * @param left
	 * @param right
	 * @return
	 */
	private List<String> wordProcess(String hanyu, char zi, int left, int right) {
		int index = hanyu.indexOf(zi);
		if (index == -1) {
			return Collections.emptyList();
		}
		if (hanyu.length() == 1) {
			return Collections.emptyList();
		}
		if (left <= 0) {
			left = 1;
		}
		if (right <= 0) {
			right = 1;
		}

		int begin = 0;
		int end = hanyu.length();
		int temp = index - left;
		if (temp > begin) {
			begin = temp;
		}
		temp = index + right + 1;
		if (temp < end) {
			end = temp;
		}
		String str = hanyu.substring(begin, end);
		List<String> list = new ArrayList<>();
		list.add(str);
		getWordListBegin2End(list, zi, str);
		getWordListEnd2Begin(list, zi, str);
		return list;
	}

	private void getWordListBegin2End(List<String> list, char zi, String subStr) {
		if (subStr.length() <= 2) {
			return;
		}
		String str = null;
		if (subStr.charAt(0) == zi) {
			str = subStr.substring(0, subStr.length() - 1);
		} else {
			str = subStr.substring(1);
		}
		list.add(str);
		getWordListBegin2End(list, zi, str);
	}

	private void getWordListEnd2Begin(List<String> list, char zi, String subStr) {
		if (subStr.length() <= 2) {
			return;
		}
		String str = null;
		if (subStr.charAt(subStr.length() - 1) == zi) {
			str = subStr.substring(1);
		} else {
			str = subStr.substring(0, subStr.length() - 1);
		}
		list.add(str);
		getWordListEnd2Begin(list, zi, str);
	}

	public static void main(String[] args) {
		DefaultDuoyinziProcess defaultDuoyinziProcess = new DefaultDuoyinziProcess();
		PrintHelper.jsonPretty(defaultDuoyinziProcess.wordProcess("大家用过都说好", '过', 3, 3));
		PrintHelper.jsonPretty(defaultDuoyinziProcess.duoyinziLib);
	}

}
