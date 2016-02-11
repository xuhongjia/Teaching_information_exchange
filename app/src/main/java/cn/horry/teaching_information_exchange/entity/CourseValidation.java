package cn.horry.teaching_information_exchange.entity;

public class CourseValidation extends Course {
	private String code;
	private Double t_x;
	private Double t_y;
	private Long validate_time;
	private Integer state;
	private Integer vId;
	private int validation_state;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

    public Integer getvId() {
        return vId;
    }

    public void setvId(Integer vId) {
        this.vId = vId;
    }

    public int getValidation_state() {
        return validation_state;
    }

    public void setValidation_state(Integer validation_state) {
        this.validation_state = validation_state;
    }
}
