package cn.sibat.warn.overtimecheck;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.sibat.warn.model.cases.CaseOverTime;
import cn.sibat.warn.model.company.CompanyInfo;
import cn.sibat.warn.model.company.CompanyWarn;
import cn.sibat.warn.model.pending.InspectPending;
import cn.sibat.warn.model.pending.LightPending;
import cn.sibat.warn.serve.tmp.dao.CompanyInfoDao;
import cn.sibat.warn.util.GetWorkingDay;
import cn.sibat.warn.util.HibSession;
import cn.sibat.warn.util.HibUtil;
import cn.sibat.warn.util.SpringContextUtil;
@Component
public class DelayCheck {
	@Autowired HibSession hs;
	@Autowired HibUtil hu;
	@Autowired CompanyInfoDao ciDao;
	Logger log = Logger.getLogger(DelayCheck.class);
	@SuppressWarnings("unchecked")
	public void work(){
		log.info("begin to check over time!!!");
		Session session = hs.getSessionFactory().openSession();
		List cilist = session.createCriteria(CompanyInfo.class).list();
		hu.batchDelete(cilist);
		session.clear();
		try {
			ciDao.listCompanyInfo();
		} catch (ParseException e) {
		}
		
		
		
		
		List<LightPending> lpList =  session.createCriteria(LightPending.class)
				.add(Restrictions.eq("status", "uncheck")).list();
		for (LightPending lp : lpList) {
			int delay = GetWorkingDay.getWorkdayTime(lp.getModify_time().getTime(), new Date().getTime())-1;
			if(delay>2){
				CaseOverTime cot = new CaseOverTime();
				cot.setCompany_id(lp.getCompany_id());
				cot.setOvertime_type("亮灯");
//				cot.setUser_id(user_id);//未设置用户id和责任人id
				hu.save(cot);
			}
		}
		
//		List<CompanyWarn> list = session.createCriteria(CompanyWarn.class).list();
//		for (CompanyWarn cw : list) {
//			String s = cw.getCompany_id();
//			LightPending lp = (LightPending) session.createCriteria(LightPending.class)
//					.add(Restrictions.eq("company_id", s))
//					.uniqueResult();
//			if(lp==null){
//				lp = new LightPending();
//				lp.setCompany_id(s);
//				lp.setCreate_time(cw.getModify_time());
//				lp.setLight_grade(cw.getLight_grade());
//				lp.setReform_type("亮灯");
////				lp.setUser_id(cw.get); 未设置跟踪者用户名
//				hu.save(lp);
//				CasePending cp = new CasePending();
//				cp.setCompany_id(s);
//				cp.setStatus("uncheck");//未设置跟踪者与调配者
//				hu.save(cp);
//			}else if(lp.getCreate_time().getTime()!=cw.getModify_time().getTime()){
//				lp.setCreate_time(cw.getModify_time());
//				hu.update(lp);
//				CasePending cp = (CasePending) session.createCriteria(CasePending.class)
//						.add(Restrictions.eq("company_id", s))
//						.uniqueResult();
//				cp.setStatus("uncheck");
//				hu.update(cp);
//			}//end if
//			
//			if(lp!=null){
//				CasePending cp = (CasePending) session.createCriteria(CasePending.class)
//						.add(Restrictions.eq("company_id", s))
//						.uniqueResult();
//				if(cp!=null&&cp.getStatus().equals("uncheck")){
//					int delay = GetWorkingDay.getWorkdayTime(cp.getCreate_time().getTime(), new Date().getTime())-1;
//					if(delay>2){
//						CaseOverTime cot = new CaseOverTime();
//						cot.setCompany_id(s);
//						cot.setOvertime_type("亮灯");
////						cot.setUser_id(user_id);//未设置用户id和责任人id
//						hu.save(cot);
//					}
//				}
//			}
//			
//			InspectPending ip = (InspectPending) session.createCriteria(InspectPending.class)
//					.add(Restrictions.eq("company_id", s))
//					.uniqueResult();
//			
//			if(ip!=null){
//				CasePending cp = (CasePending) session.createCriteria(CasePending.class)
//						.add(Restrictions.eq("company_id", s))
//						.uniqueResult();
//				if(cp!=null&&cp.getStatus().equals("uncheck")){
//					int delay = GetWorkingDay.getWorkdayTime(cp.getCreate_time().getTime(), new Date().getTime())-1;
//					if(delay>15){
//						CaseOverTime cot = new CaseOverTime();
//						cot.setCompany_id(s);
//						cot.setOvertime_type("巡查");
////						cot.setUser_id(user_id);//未设置用户id和责任人id
//						hu.save(cot);
//					}
//				}
//			}
//			
//		}
		
	}
	
	
	
	@PreDestroy
	public void destroy() throws InterruptedException{
		Thread.sleep(1000);
	}
	
	
}
