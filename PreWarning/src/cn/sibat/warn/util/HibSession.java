package cn.sibat.warn.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Repository;
@Repository
public class HibSession implements DisposableBean{
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
	
	
	@Override
	public void destroy() throws Exception {
		this.sessionFactory.close();
		
	}

}
