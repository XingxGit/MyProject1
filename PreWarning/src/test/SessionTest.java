package test;

import java.util.Date;

import org.hibernate.Session;

import cn.sibat.warn.model.company.CompanyWarn;
import cn.sibat.warn.model.user.User;
import cn.sibat.warn.util.HibSession;

public class SessionTest {
public static void main(String[] args) {
	HibSession hb = HibSession.getInstance();
	if(hb.getSessionFactory()==null)
		System.out.println("sessionFactory is null");
	Session session = hb.getSessionFactory().openSession();
	session.createCriteria(User.class)
	.setCacheable(true)
	.list();
	
//	session.get(User.class, "2");
	
	Session session2 = hb.getSessionFactory().openSession();
	session2.createCriteria(User.class)
	.setCacheable(true)
	.list();
	CompanyWarn cw = new CompanyWarn();
	cw.setCompany_id("345");
	cw.setCreate_time(new Date());
	session.beginTransaction();
	session.save(cw);
	session.getTransaction().commit();
	session.close();
	
	
	
	
//	session2.get(User.class, "2");
	hb.getSessionFactory().close();
	
}
}
