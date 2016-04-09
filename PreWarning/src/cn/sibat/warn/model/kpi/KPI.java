package cn.sibat.warn.model.kpi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.sibat.warn.util.HashUtil;

@Entity( name = "kpi")
public class KPI implements Serializable{
	private static final long serialVersionUID = 3239048634758235418L;
	private String id;
	private String kpi_id;
	private String kpi_name;
	private Double kpi_weight;
	private String agency;
	private Double green_weight;
	private Double blue_weight;
	private Double yellow_weight;
	private Double red_weight;
	private String measurement;
	private Date create_time;
	
	
	public KPI() {
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
	public String getKpi_name() {
		return kpi_name;
	}
	public void setKpi_name(String kpi_name) {
		this.kpi_name = kpi_name;
	}
	
	
	public String getKpi_id() {
		return kpi_id;
	}
	public void setKpi_id(String kpi_id) {
		this.kpi_id = kpi_id;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	public Double getKpi_weight() {
		return kpi_weight;
	}
	public void setKpi_weight(Double kpi_weight) {
		this.kpi_weight = kpi_weight;
	}
	public Double getGreen_weight() {
		return green_weight;
	}
	public void setGreen_weight(Double green_weight) {
		this.green_weight = green_weight;
	}
	public Double getBlue_weight() {
		return blue_weight;
	}
	public void setBlue_weight(Double blue_weight) {
		this.blue_weight = blue_weight;
	}
	public Double getYellow_weight() {
		return yellow_weight;
	}
	public void setYellow_weight(Double yellow_weight) {
		this.yellow_weight = yellow_weight;
	}
	public Double getRed_weight() {
		return red_weight;
	}
	public void setRed_weight(Double red_weight) {
		this.red_weight = red_weight;
	}
	public String getMeasurement() {
		return measurement;
	}
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
	
	
	

}
