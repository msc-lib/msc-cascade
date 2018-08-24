package fun.pplm.msc.cascade.utils;

public interface DuoyinziProcess {
	
	public static final DuoyinziProcess DEFAULT = new DefaultDuoyinziProcess();
	/**
	 * 
	 * @param huanyu 完整的汉语
	 * @param zi 多音字
	 * @return
	 */
	public String process(String huanyu, char zi, String[] pinyin);
	
}
