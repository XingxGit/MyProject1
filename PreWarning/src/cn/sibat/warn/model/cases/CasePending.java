package cn.sibat.warn.model.cases;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.sibat.warn.util.HashUtil;
@Entity( name = "case_pending")
public class CasePending implements Serializable{
	private static final long serialVersionUID = -4137105504380076226L;
	private String company_id;
	private String user_id;
	private String duty_id;
	private Date create_time;
	private String status;
	public CasePending() {
		super();
		this.create_time = new Date();
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
	public String getDuty_id() {
		return duty_id;
	}
	public void setDuty_id(String duty_id) {
		this.duty_id = duty_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
