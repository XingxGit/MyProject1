package cn.sibat.warn.model.pending;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.sibat.warn.util.HashUtil;
@Entity( name = "light_pending")
public class LightPending {
	private String company_id;
	private String user_id;
	private String light_grade;
	private Date create_time;
	private Date modify_time;
	private String reform_type;
	private String status;
	public LightPending() {
		super();
		this.create_time = new Date();
		this.modify_time = new Date();
		this.reform_type = "亮灯";
		this.status = "uncheck";
	}
	@Id
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getLight_grade() {
		return light_grade;
	}
	public void setLight_grade(String light_grade) {
		this.light_grade = light_grade;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getReform_type() {
		return reform_type;
	}
	public void setReform_type(String reform_type) {
		this.reform_type = reform_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	
	
	
}
