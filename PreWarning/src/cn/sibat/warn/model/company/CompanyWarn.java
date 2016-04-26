package cn.sibat.warn.model.company;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity( name = "company_warn")
public class CompanyWarn {
	private Date create_time;
	private Date modify_time;
	private String company_id;
	private Double green_score;
	private Double blue_score;
	private Double yellow_score;
	private Double red_score;
	private String light_grade;
	private String time;
	private String street_name;
	
	
	
	
	public CompanyWarn() {
		super();
		this.create_time = new Date();
	}
	
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Id
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	
	
	public String getLight_grade() {
		return light_grade;
	}
	public void setLight_grade(String light_grade) {
		this.light_grade = light_grade;
	}
	@Column(scale=3)
	public Double getGreen_score() {
		return green_score;
	}

	public void setGreen_score(Double green_score) {
		this.green_score = green_score;
	}
	@Column(scale=3)
	public Double getBlue_score() {
		return blue_score;
	}
	
	public void setBlue_score(Double blue_score) {
		this.blue_score = blue_score;
	}
	@Column(scale=3)
	public Double getYellow_score() {
		return yellow_score;
	}
	
	public void setYellow_score(Double yellow_score) {
		this.yellow_score = yellow_score;
	}
	@Column(scale=3)
	public Double getRed_score() {
		return red_score;
	}

	public void setRed_score(Double red_score) {
		this.red_score = red_score;
	}

	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	
	
	

}
