package fun.pplm.msc.cascade.utils;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public final class PyHelper {
	/**
	 * 使用该方法将首字母变成拼音，后续可以按拼音进行排序。
	 */
	public static String getPingYin(String hanyu) {
		return getPinyin(hanyu, null);
	}
	
	public static String getPinyin(String hanyu, DuoyinziProcess duoyinziProcess) {
		if (duoyinziProcess == null) {
			duoyinziProcess = DuoyinziProcess.DEFAULT;
		}
		String hanyuPinYin = "";
		for (char c : hanyu.toCharArray()) {
			try {
				String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, getDefaultOutputFormat());
				if (pinyin != null) {
					if (pinyin.length > 1) {
						if (same(pinyin)) {
							hanyuPinYin += pinyin[0];
						} else {
							String duoyin = duoyinziProcess.process(hanyu, c);
							if (StringUtils.isNotBlank(duoyin)) {
								hanyuPinYin += duoyin;
							} else {
								System.out.println("duoyinzi [" + c + "] can't process in [" + hanyu + "]");
								hanyuPinYin += pinyin[0];
							}
						}
						PrintHelper.jsonPretty(hanyu);
						PrintHelper.jsonPretty(c);
						PrintHelper.jsonPretty(pinyin);
					} else {
						hanyuPinYin += pinyin[0];
					}
				} else {
					hanyuPinYin += c;
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		}
		return hanyuPinYin;
	}

	/**
	 * 判断数组中的所有字符串是否相同
	 * @param arr
	 * @return
	 */
	private static boolean same(String[] arr) {
		if (arr.length <= 1) {
			return true;
		}
		String temp = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (!temp.equals(arr[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 以字为中心（左侧第一个开始），获取字的left right两端的各种组合
	 * 举例：大家用过都说好, 过, 1, 1; 结果：用过，过都，用过都 
	 * 举例：大家用过都说好, 过, 0, 1; 结果：过都
	 * 举例：大家用过都说好, 过, 2, 0; 结果：家用过，用过
	 * 举例：大家用过都说好, 过, 2, 1; 结果：家用过都，家用过，用过，用过都， 过都
	 * @param hanyu
	 * @param zi
	 * @param left
	 * @param right
	 * @return
	 */
	private static List<String> wordProcess(String hanyu, char zi, int left, int right) {
		int index = hanyu.indexOf(zi);
		if (index == -1) {
			return Collections.emptyList();
		}
		
		return null;
	}
	
	private static HanyuPinyinOutputFormat getDefaultOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
		return format;
	}
	
	public static void main(String[] args) {
		System.out.println(PyHelper.getPingYin("天津"));
	}
}
