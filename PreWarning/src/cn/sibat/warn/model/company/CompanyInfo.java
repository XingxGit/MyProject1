package cn.sibat.warn.model.company;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.sibat.warn.util.HashUtil;

@Entity( name = "company_info")
public class CompanyInfo implements Serializable{
	private static final long serialVersionUID = 3319771014481485461L;
	private String id;
	private String company_id;
	private String business_id;
	private String company_scale;
	private String address;
	private String Contactor;
	private String telephone;
	private String company_name;
	private String street_name;
	private String is_case;
	private Date create_time;
	
	
	public CompanyInfo() {
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
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	
	public String getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	public String getCompany_scale() {
		return company_scale;
	}
	public void setCompany_scale(String company_scale) {
		this.company_scale = company_scale;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactor() {
		return Contactor;
	}
	public void setContactor(String contactor) {
		Contactor = contactor;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getStreet_name() {
		return street_name;
	}
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	public String getIs_case() {
		return is_case;
	}
	public void setIs_case(String is_case) {
		this.is_case = is_case;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	

}
