package cn.sibat.warn.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import cn.sibat.warn.model.user.User;
import cn.sibat.warn.service.AlgoExcutorService;
import cn.sibat.warn.util.HibSession;
import cn.sibat.warn.util.SpringContextUtil;


public class ConnectionListener implements ServletContextListener{
	Timer timer;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
//			Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("startQuertz");
//			if(scheduler!=null){
//				System.out.println("scheduler is not null!");
//			}
//            scheduler.shutdown();
//            Thread.sleep(1000);
			
			AlgoExcutorService.getInstance().getExecutorService().shutdown();
			timer.cancel();
			AbandonedConnectionCleanupThread.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		timer = new Timer(); 	
	    timer.schedule(AlgoExcutorService.getInstance(), 0,10 * 1000);
	}
}

