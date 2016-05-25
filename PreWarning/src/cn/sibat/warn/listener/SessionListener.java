package cn.sibat.warn.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionListener implements HttpSessionListener{
	Logger log = Logger.getLogger("framework");
	@Override
	public void sessionCreated(HttpSessionEvent event) {
			String id = event.getSession().getId();
//			log.info("session id = "+id);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
