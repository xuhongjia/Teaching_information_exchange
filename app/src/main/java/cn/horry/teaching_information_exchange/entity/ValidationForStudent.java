package cn.horry.teaching_information_exchange.entity;

/**
 * Created by Administrator on 2015/12/15.
 */
public class ValidationForStudent {
    private Integer id;
    private String code;
    private Double s_x;
    private Double s_y;
    private Long validate_time;
    private Integer vId;
    private Integer uId;
    private Integer state;
    private String name;
    private String img;
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

    public Double getS_x() {
        return s_x;
    }

    public void setS_x(Double s_x) {
        this.s_x = s_x;
    }

    public Long getValidate_time() {
        return validate_time;
    }

    public void setValidate_time(Long validate_time) {
        this.validate_time = validate_time;
    }

    public Double getS_y() {
        return s_y;
    }

    public void setS_y(Double s_y) {
        this.s_y = s_y;
    }

    public Integer getvId() {
        return vId;
    }

    public void setvId(Integer vId) {
        this.vId = vId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
