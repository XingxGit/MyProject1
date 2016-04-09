package cn.sibat.warn.serve.hib.dao;

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
import cn.sibat.warn.util.HibSession;
@SuppressWarnings("unchecked")
@Service
public class CaseDao {
	static Logger log = Logger.getLogger(CaseDao.class);
	@Autowired HibSession hs;
	public void saveCase(CaseUpload cs){
		Session session = hs.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(cs);
		session.getTransaction().commit();
		session.close();
	}
	
	
	public List searchCase(String company_id, String agency){
		Session session = hs.getSessionFactory().openSession();
		List list = null;
		
		if(agency!=null&&!agency.equals("")){
		list = session.createCriteria(CaseUpload.class)
		.add(Restrictions.eq("company_id", company_id))
		.add(Restrictions.eq("agency", agency))
		.list();
		}else{
		list = session.createCriteria(CaseUpload.class)
				.add(Restrictions.eq("company_id", company_id))
				.list();
		}
		return list;
	}
	
	
	public List searchRandomCompany(){
		Session session = hs.getSessionFactory().openSession();
		List list = session.createCriteria(CompanyInfo.class)
				.list();
		return list;
	}
	
	public void  saveCompany(CompanyInfo ci){
		Session session = hs.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(ci);
		session.getTransaction().commit();
		session.close();
		
	}
	
	public List searchCompany(String input){
		if(input==null||"".equals(input))
			return null;
		Session session = hs.getSessionFactory().openSession();
		List list = session.createCriteria(CompanyInfo.class)
				.add(Restrictions.or(Restrictions.like("company_id", input,MatchMode.ANYWHERE), Restrictions.like("company_name", input,MatchMode.ANYWHERE)))
				.list();
		
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
