package cn.sibat.warn.serve.hib.dao;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sibat.warn.model.cases.CaseUpload;
import cn.sibat.warn.model.company.CompanyInfo;
import cn.sibat.warn.model.company.CompanyWarn;
import cn.sibat.warn.model.kpi.KPI;
import cn.sibat.warn.serve.tmp.dao.CompanyInfoDao;
import cn.sibat.warn.util.HibSession;
import cn.sibat.warn.util.HibUtil;
@SuppressWarnings("unchecked")
@Service
public class CaseDao {
	static Logger log = Logger.getLogger(CaseDao.class);
	@Autowired HibSession hs;
	@Autowired HibUtil hu;
	@Autowired CompanyInfoDao cid;
	public void saveCase(CaseUpload cs){
		hu.save(cs);
	}
	
	
	public List searchCase(String company_id, String agency){
		Session session = hs.getSessionFactory().openSession();
		List list = null;
		list = session.createCriteria(CaseUpload.class)
		.add(Restrictions.eq("company_id", company_id))
		.add(Restrictions.eq("agency", agency))
		.list();
		return list;
	}
	
	public List searchCase(String company_id){
		Session session = hs.getSessionFactory().openSession();
		List list = null;
		list = session.createCriteria(CaseUpload.class)
				.add(Restrictions.eq("company_id", company_id))
				.list();
		return list;
	}
	
	public void saveListCase(List list){
		hu.batchSave(list);
	}
	
	public void updateCase(CaseUpload cu){
		hu.update(cu);
	}
	
	public CaseUpload searchCaseByIds(String company_id, String kpi_ids){
		Session session = hs.getSessionFactory().openSession();
		List list = null;
		if(company_id==null||kpi_ids==null)return null;
		list = session.createCriteria(CaseUpload.class)
		.add(Restrictions.eq("company_id", company_id))
		.add(Restrictions.eq("kpi_ids", kpi_ids))
		.list();
		if(list!=null&&list.size()>0)
			return (CaseUpload) list.get(0);
		else
			return null;
	}
	
	
	public List searchRandomCompany(){
		List list = null;
		try {
			list = cid.listCompanyInfo();
		} catch (ParseException e) {
		}
		return list;
	}
	
	public void  saveCompany(CompanyInfo ci){
		hu.save(ci);
		
	}
	
	public List searchCompany(String input){
		if(input==null||"".equals(input))
			return null;
		List list = null;
		try {
			list = cid.getCompanyInfo(input);
		} catch (ParseException e) {
		}
		return list;
	}
	
	public List searchLightCase(String light_grade){
		Session session = hs.getSessionFactory().openSession();
		List list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("light_grade", light_grade))
				.list();
		
		return list;
	}
	
	public CompanyWarn searchWarnCompany(String company_id){
		Session session = hs.getSessionFactory().openSession();
		List list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("company_id", company_id))
				.list();
				
		if(list!=null&&list.size()>0){
			return (CompanyWarn) list.get(0);
		}else{
			return null;
		}
	}
	
	public KPI searchKPI(String kpi_name){
		Session session = hs.getSessionFactory().openSession();
		List list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("kpi_name", kpi_name))
				.list();
		
		if(list!=null&&list.size()>0){
			return (KPI) list.get(0);
		}else{
			return null;
		}
	}
	
	
	
//	public static void main(String[] args) {
//		List list = searchCompany("1");
//		System.out.println(list.size());
//	}
}
