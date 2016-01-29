package cn.horry.teaching_information_exchange.entity;

public class Validation {
	private Integer id;
	private String code;
	private Double t_x;
	private Double t_y;
	private Long validate_time;
	private Integer cId;
	private Integer state;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getT_x() {
		return t_x;
	}
	public void setT_x(Double t_x) {
		this.t_x = t_x;
	}
	public Double getT_y() {
		return t_y;
	}
	public void setT_y(Double t_y) {
		this.t_y = t_y;
	}
	public Long getValidate_time() {
		return validate_time;
	}
	public void setValidate_time(Long validate_time) {
		this.validate_time = validate_time;
	}
	public Integer getcId() {
		return cId;
	}
	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
