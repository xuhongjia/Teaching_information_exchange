package cn.horry.teaching_information_exchange.entity;

public class CourseFeedBack extends Course {
	private Integer fId;
	private String feed_content;//反馈内容
	private String student_name;
	private Integer student_id;
	private String student_img;
	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}
	public String getFeed_content() {
		return feed_content;
	}

	public void setFeed_content(String feed_content) {
		this.feed_content = feed_content;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}

	public String getStudent_img() {
		return student_img;
	}

	public void setStudent_img(String student_img) {
		this.student_img = student_img;
	}
	
}
