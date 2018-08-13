package fun.pplm.msc.cascade.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PyHelper {
	/**
	 * 使用该方法将首字母变成拼音，后续可以按拼音进行排序。
	 */
	public static String getPingYin(String hanyu) {
		String hanyuPinYin = "";
		for (char c : hanyu.toCharArray()) {
			try {
				String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, getDefaultOutputFormat());
				if (pinyin != null) {
					hanyuPinYin += pinyin[0];
				} else {
					hanyuPinYin += c;
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		}
		return hanyuPinYin;
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
