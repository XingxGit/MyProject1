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
//			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//	
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	}

}
