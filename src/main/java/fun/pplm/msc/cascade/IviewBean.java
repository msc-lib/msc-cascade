package fun.pplm.msc.cascade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class IviewBean {
	private String value;
	private String label;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String pinyin;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<IviewBean> children;
	
	public IviewBean() {
		super();
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
