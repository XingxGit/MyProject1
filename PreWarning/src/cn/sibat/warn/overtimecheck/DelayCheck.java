package cn.sibat.warn.overtimecheck;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import cn.sibat.warn.model.cases.CaseOverTime;
import cn.sibat.warn.model.cases.CasePending;
import cn.sibat.warn.model.company.CompanyWarn;
import cn.sibat.warn.model.pending.LightPending;
import cn.sibat.warn.util.GetWorkingDay;
import cn.sibat.warn.util.HibSession;
import cn.sibat.warn.util.HibUtil;

public class DelayCheck {
	@Autowired HibSession hs;
	@Autowired HibUtil hu;
	public void work(){
		System.out.println("begin to work!!!");
		Session session = hs.getSessionFactory().openSession();
		List<CompanyWarn> list = session.createCriteria(CompanyWarn.class).list();
		for (CompanyWarn cw : list) {
			String s = cw.getCompany_id();
			LightPending lp = (LightPending) session.createCriteria(LightPending.class)
					.add(Restrictions.eq("company_id", s))
					.uniqueResult();
			if(lp==null){
				lp = new LightPending();
				lp.setCompany_id(s);
				lp.setCreate_time(cw.getModify_time());
				lp.setLight_grade(cw.getLight_grade());
				lp.setReform_type("亮灯");
//				lp.setUser_id(cw.get); 未设置跟踪者用户名
				hu.save(lp);
				CasePending cp = new CasePending();
				cp.setCompany_id(s);
				cp.setStatus("uncheck");//未设置跟踪者与调配者
				hu.save(cp);
			}else if(lp.getCreate_time().getTime()!=cw.getModify_time().getTime()){
				lp.setCreate_time(cw.getModify_time());
				hu.update(lp);
				CasePending cp = (CasePending) session.createCriteria(CasePending.class)
						.add(Restrictions.eq("company_id", s))
						.uniqueResult();
				cp.setStatus("uncheck");
				hu.update(cp);
			}//end if
			
			if(lp!=null){
				CasePending cp = (CasePending) session.createCriteria(CasePending.class)
						.add(Restrictions.eq("company_id", s))
						.uniqueResult();
				if(cp!=null&&cp.getStatus().equals("uncheck")){
					int delay = GetWorkingDay.getWorkdayTime(cp.getCreate_time().getTime(), new Date().getTime())-1;
					if(delay>2){
						CaseOverTime cot = new CaseOverTime();
						cot.setCompany_id(s);
						cot.setOvertime_type("亮灯");
//						cot.setUser_id(user_id);//未设置用户id和责任人id
						hu.save(cot);
					}
				}
			}
			
			
		}
		
		
		
		
	}
}