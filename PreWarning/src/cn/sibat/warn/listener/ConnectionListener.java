package cn.sibat.warn.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import cn.sibat.warn.model.user.User;
import cn.sibat.warn.util.HibSession;


public class ConnectionListener implements ServletContextListener{
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			
			AbandonedConnectionCleanupThread.shutdown();
//			HibSession hs = HibSession.getInstance();
//			hs.getSessionFactory().close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		HibSession hs = HibSession.getInstance();
//		Session session = hs.getSessionFactory().openSession();
//		session.createCriteria(User.class).list();
	}

}
