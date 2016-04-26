package cn.sibat.warn.serve.tmp.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.sibat.warn.model.company.CompanyInfo;

@Service
public class CompanyInfoDao {
	@Autowired JdbcTemplate jdbcTemplate;
	public CompanyInfo getCompanyInfo(String company_id){
		CompanyInfo ci = new CompanyInfo();
		return ci;
	}
	
	public List getCompanyInfo(List  list){
		List infoList = new ArrayList<>();
		return infoList;
	}
}
