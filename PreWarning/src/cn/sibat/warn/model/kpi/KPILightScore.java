package cn.sibat.warn.model.kpi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity( name = "kpi_light_score")
public class KPILightScore implements Serializable{
	private static final long serialVersionUID = 649049079747598675L;
	private Long id;
	private String first_kpi;
	private String second_kpi;
	private String third_kpi;
	private Double green_score;
	private Double yellow_score;
	private Double blue_score;
	private Double red_score;
	private Date create_time;
	
	
	
	public KPILightScore() {
		super();
		this.create_time = new Date();
	}
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirst_kpi() {
		return first_kpi;
	}
	public void setFirst_kpi(String first_kpi) {
		this.first_kpi = first_kpi;
	}
	public String getSecond_kpi() {
		return second_kpi;
	}
	public void setSecond_kpi(String second_kpi) {
		this.second_kpi = second_kpi;
	}
	public String getThird_kpi() {
		return third_kpi;
	}
	public void setThird_kpi(String third_kpi) {
		this.third_kpi = third_kpi;
	}
	
	public Double getGreen_score() {
		return green_score;
	}
	public void setGreen_score(Double green_score) {
		this.green_score = green_score;
	}
	public Double getYellow_score() {
		return yellow_score;
	}
	public void setYellow_score(Double yellow_score) {
		this.yellow_score = yellow_score;
	}
	public Double getBlue_score() {
		return blue_score;
	}
	public void setBlue_score(Double blue_score) {
		this.blue_score = blue_score;
	}
	public Double getRed_score() {
		return red_score;
	}
	public void setRed_score(Double red_score) {
		this.red_score = red_score;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	
	

}
