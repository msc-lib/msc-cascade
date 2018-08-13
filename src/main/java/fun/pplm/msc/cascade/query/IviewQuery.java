package fun.pplm.msc.cascade.query;

import java.util.List;

public class IviewQuery {
	private List<String> provValues;
	private List<String> cityValues;
	private List<String> areaValues;
	private Integer deep = 2;
	
	public IviewQuery() {
		super();
	}

	public List<String> getProvValues() {
		return provValues;
	}

	public void setProvValues(List<String> provValues) {
		this.provValues = provValues;
	}

	public List<String> getCityValues() {
		return cityValues;
	}

	public void setCityValues(List<String> cityValues) {
		this.cityValues = cityValues;
	}

	public List<String> getAreaValues() {
		return areaValues;
	}

	public void setAreaValues(List<String> areaValues) {
		this.areaValues = areaValues;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}
	
}
