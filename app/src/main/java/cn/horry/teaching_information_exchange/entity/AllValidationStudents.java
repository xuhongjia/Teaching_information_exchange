package cn.horry.teaching_information_exchange.entity;

import java.util.List;

public class AllValidationStudents {
	private List<ValidationForStudent> datas;
	private Integer count;
	public List<ValidationForStudent> getDatas() {
		return datas;
	}
	public void setDatas(List<ValidationForStudent> datas) {
		this.datas = datas;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
