package cn.sibat.warn.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;

import cn.sibat.warn.model.user.User;
@Repository
public class HibSession {
//	private static final HibSession hs = new HibSession();
	private SessionFactory sessionFactory = null;
	public static HibSession getInstance() {
		return new HibSession();
	}
	public SessionFactory getSessionFactory(){
		return this.sessionFactory;
	}
	
	
	
	public HibSession() {
		super();
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	 public  void setUp() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() 
				.build();
		try {
			 sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
			
		}
		catch (Exception e) {
			
			StandardServiceRegistryBuilder.destroy( registry );
		}
	
		
	}
	
	public static void main(String[] args) throws Exception {
		HibSession hs = new HibSession();
		Session session = hs.getSessionFactory().openSession();
		session.beginTransaction();
		User user = new User();
		user.setId("0409");
		user.setUser_id("zzz");
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}

}
