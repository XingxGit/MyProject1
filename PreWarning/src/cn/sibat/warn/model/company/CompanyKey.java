package cn.sibat.warn.model.company;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.sibat.warn.util.HashUtil;
@Entity( name = "company_key")
public class CompanyKey {
	private String id;
	private String skey;
	private Date create_time;
	
	
	public CompanyKey() {
		this.id = HashUtil.getRandomId();
	}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSkey() {
		return skey;
	}
	public void setSkey(String skey) {
		this.skey = skey;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	

}
