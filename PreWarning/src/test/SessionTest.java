package test;

import org.hibernate.Session;

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
//	session2.get(User.class, "2");
}
}
