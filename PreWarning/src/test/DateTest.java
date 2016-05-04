package test;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import cn.sibat.warn.model.company.CompanyWarn;
import cn.sibat.warn.model.user.User;
import cn.sibat.warn.util.HibSession;

public class DateTest {
	public static void main(String[] args) {
		HibSession hb = HibSession.getInstance();
	if(hb.getSessionFactory()==null)
		System.out.println("sessionFactory is null");
	CompanyWarn cw = new CompanyWarn();
	cw.setCompany_id("test1");
	Session session = hb.getSessionFactory().openSession();
	session.beginTransaction();
	session.save(cw);
	session.getTransaction().commit();
	session.flush();
	CompanyWarn cw1= (CompanyWarn) session.createCriteria(CompanyWarn.class).add(Restrictions.eq("company_id", cw.getCompany_id())).uniqueResult();
	CompanyWarn cw2 = new CompanyWarn();
	cw2.setCreate_time(cw1.getCreate_time());
	cw2.setCompany_id("test2");
	session.beginTransaction();
	session.save(cw2);
	session.getTransaction().commit();
	session.flush();
	session.close();
	
	}
	
}
