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
	private String industry_name;
	private String company_size;
	private String company_address;
	private String contacts_name;
	private String contacts_ways;
	private String company_name;
	private String street_name;
	private String is_case;
	private Date create_time;
	
	
	public CompanyInfo() {
		super();
		this.create_time = new Date();
		this.id = HashUtil.getRandomId();
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
	
	
	
	public String getIndustry_name() {
		return industry_name;
	}
	public void setIndustry_name(String industry_name) {
		this.industry_name = industry_name;
	}
	public String getCompany_size() {
		return company_size;
	}
	public void setCompany_size(String company_size) {
		this.company_size = company_size;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getContacts_name() {
		return contacts_name;
	}
	public void setContacts_name(String contacts_name) {
		this.contacts_name = contacts_name;
	}
	public String getContacts_ways() {
		return contacts_ways;
	}
	public void setContacts_ways(String contacts_ways) {
		this.contacts_ways = contacts_ways;
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
