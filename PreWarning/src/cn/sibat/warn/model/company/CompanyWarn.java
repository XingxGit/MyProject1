package cn.sibat.warn.model.company;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.sibat.warn.util.HashUtil;

@Entity( name = "company_warn")
public class CompanyWarn {
	private Date create_time;
	private String company_id;
	private Double green_score;
	private Double blue_score;
	private Double yellow_score;
	private Double red_score;
	private String light_grade;
	
	
	
	
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

	public Double getGreen_score() {
		return green_score;
	}

	public void setGreen_score(Double green_score) {
		this.green_score = green_score;
	}

	public Double getBlue_score() {
		return blue_score;
	}

	public void setBlue_score(Double blue_score) {
		this.blue_score = blue_score;
	}

	public Double getYellow_score() {
		return yellow_score;
	}

	public void setYellow_score(Double yellow_score) {
		this.yellow_score = yellow_score;
	}

	public Double getRed_score() {
		return red_score;
	}

	public void setRed_score(Double red_score) {
		this.red_score = red_score;
	}
	
	
	

}
