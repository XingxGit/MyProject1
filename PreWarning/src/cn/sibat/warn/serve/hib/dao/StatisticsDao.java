package cn.sibat.warn.serve.hib.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sibat.warn.model.company.CompanyWarn;
import cn.sibat.warn.util.HibSession;
import net.sf.json.JSONObject;
@Service
public class StatisticsDao {
	@Autowired HibSession hs;
	public  JSONObject getCompanyLightsVol(){
//		HibSession hs = HibSession.getInstance();
		Session session = hs.getSessionFactory().openSession();
		
		JSONObject obj = new JSONObject();
		List criterions = new ArrayList();
		List redList = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("light_grade", "red")).list();
		
		if(redList!=null){
			obj.put("red", redList.size());
		}else{
			obj.put("red", 0);
		}
		session.flush();
		List yellowList = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("light_grade", "yellow")).list();
				
		
		if(yellowList!=null){
			obj.put("yellow", yellowList.size());
		}else{
			obj.put("yellow", 0);
		}
		
		session.flush();
		List blueList = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("light_grade", "blue")).list();
		
		if(blueList!=null){
			obj.put("blue", blueList.size());
		}else{
			obj.put("blue", 0);
		}
		session.flush();
		
		List greenList = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("light_grade", "green")).list();
		if(greenList!=null){
			obj.put("green", greenList.size());
		}else{
			obj.put("green", 0);
		}
		return obj;
	}
	
}
