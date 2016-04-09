package cn.sibat.warn.model.cases;

import java.io.Serializable;
import java.util.Date;

import cn.sibat.warn.util.HashUtil;

public class CasePending implements Serializable{
	private static final long serialVersionUID = -4137105504380076226L;
	private String id;
	private String company_id;
	private String user_id;
	private String duty_id;
	private Date create_time;
	public CasePending() {
		super();
		this.id = HashUtil.getRandomId();
		this.create_time = new Date();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	
	

}
