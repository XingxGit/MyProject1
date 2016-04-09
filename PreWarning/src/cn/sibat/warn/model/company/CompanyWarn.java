package cn.sibat.warn.model.company;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.sibat.warn.util.HashUtil;

@Entity( name = "company_warn")
public class CompanyWarn {
	private String id;
	private Date create_time;
	private String company_id;
	private Integer score;
	private String light_grade;
	private String kpi_ids;
	private String street_name;
	
	
	
	public CompanyWarn() {
		super();
		this.id = HashUtil.getRandomId();
		this.create_time = new Date();
	}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getLight_grade() {
		return light_grade;
	}
	public void setLight_grade(String light_grade) {
		this.light_grade = light_grade;
	}
	public String getKpi_ids() {
		return kpi_ids;
	}
	public void setKpi_ids(String kpi_ids) {
		this.kpi_ids = kpi_ids;
	}
	public String getStreet_name() {
		return street_name;
	}
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	
	

}
