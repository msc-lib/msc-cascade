package fun.pplm.msc.cascade.utils;

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
					processYu2v(pinyin);
					if (pinyin.length > 1) {
						if (same(pinyin)) {
							hanyuPinYin += pinyin[0];
						} else {
							String duoyin = duoyinziProcess.process(hanyu, c, pinyin);
							if (StringUtils.isNotBlank(duoyin)) {
								hanyuPinYin += duoyin;
							} else {
								System.out.println("duoyinzi [" + c + "] can't process in [" + hanyu + "]");
								hanyuPinYin += pinyin[0];
							}
						}
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

	private static void processYu2v(String[] pinyin) {
		if (pinyin != null && pinyin.length > 0) {
			for (int i = 0; i < pinyin.length; i++) {
				pinyin[i] = pinyin[i].replace("u:", "v");
			}
		}
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
	
	private static HanyuPinyinOutputFormat getDefaultOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
		return format;
	}
	
	public static void main(String[] args) {
		System.out.println(PyHelper.getPingYin("长沙"));
	}
}
