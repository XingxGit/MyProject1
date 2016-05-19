package cn.sibat.warn.model.cases;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.sibat.warn.util.HashUtil;

@Entity( name = "case_upload")
public class CaseUpload implements Serializable{
	private static final long serialVersionUID = 9111484573991217771L;
	private String id;
	private String company_id;
	private String kpi_ids;
	private String street_id;
	private String remarks;
	private String agency;
	private String user_id;
	private Integer prior;
	private Date upload_time;
	private Double value;
	private String source;
	public CaseUpload() {
		super();
		this.id = HashUtil.getRandomId();
		this.upload_time = new Date();
	}
	public Date getUpload_time() {
		return upload_time;
	}
	public void setUpload_time(Date upload_time) {
		this.upload_time = upload_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Integer getPrior() {
		return prior;
	}
	public void setPrior(Integer prior) {
		this.prior = prior;
	}
	
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getKpi_ids() {
		return kpi_ids;
	}
	public void setKpi_ids(String kpi_ids) {
		this.kpi_ids = kpi_ids;
	}
	public String getStreet_id() {
		return street_id;
	}
	public void setStreet_id(String street_id) {
		this.street_id = street_id;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	

}
