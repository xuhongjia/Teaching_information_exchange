package cn.horry.teaching_information_exchange.entity;

public class User {
	private Integer id;
	private String account;
	private String password;
	private String name;
	private Integer age;
	private Integer sex;
	private Integer isTeacher;
	private String img;
	private Long LastLoginTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getIsTeacher() {
		return isTeacher;
	}
	public void setIsTeacher(Integer isTeacher) {
		this.isTeacher = isTeacher;
	}
	public Long getLastLoginTime() {
		return LastLoginTime;
	}
	public void setLastLoginTime(Long lastLoginTime) {
		LastLoginTime = lastLoginTime;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
}
