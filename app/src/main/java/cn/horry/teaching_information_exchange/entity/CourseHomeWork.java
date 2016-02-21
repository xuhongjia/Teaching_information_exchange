package cn.horry.teaching_information_exchange.entity;


public class CourseHomeWork extends Course {
	private Integer hId;
	private String t_name;
	public Integer gethId() {
		return hId;
	}
	public void sethId(Integer hId) {
		this.hId = hId;
	}
    @Override
    public String getT_name() {
        return t_name;
    }

    @Override
    public void setT_name(String t_name) {
        this.t_name = t_name;
    }
}
