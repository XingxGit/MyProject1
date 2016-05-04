package cn.sibat.warn.model.cases;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity( name = "case_overtime")
public class CaseOverTime {
	private String company_id;
	private String user_id;
	private String duty_id;
	private String overtime_type;
	private Date create_time;
	public CaseOverTime() {
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
	public String getOvertime_type() {
		return overtime_type;
	}
	public void setOvertime_type(String overtime_type) {
		this.overtime_type = overtime_type;
	}
	
	
}
