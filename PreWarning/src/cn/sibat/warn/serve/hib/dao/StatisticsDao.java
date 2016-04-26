package cn.sibat.warn.serve.hib.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
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
		Session session = hs.getSessionFactory().openSession();
		JSONObject obj = new JSONObject();
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
	@SuppressWarnings("unchecked")
	public JSONObject getWarnCompanyVol(String time){
		Session session = hs.getSessionFactory().openSession();
		JSONObject obj = new JSONObject();
		Integer xinan = 0;
		Integer xixiang = 0;
		Integer fuyong = 0;
		Integer shajing = 0;
		Integer songgang = 0;
		Integer shiyan = 0;
		if(time!=null&&time.equals("0")){
			String s = new SimpleDateFormat("yyyy").format(new Date());
			
			List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
			.add(Restrictions.like("time", s, MatchMode.ANYWHERE)).list();
			for (CompanyWarn cw : list) {
				switch(cw.getStreet_name()){
					case "新安":xinan++;break;
					case "西乡":xixiang++;break;
					case "福永":fuyong++;break;
					case "沙井":shajing++;break;
					case "松岗":songgang++;break;
					case "石岩":shiyan++;break;
				}
			}
			
		}else if(time!=null){
			String s = "";
			if(time.length()==1){
				s = "-0"+time+"-";
			}else{
				s = "-"+time+"-";
			}
			List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
			.add(Restrictions.like("time", s, MatchMode.ANYWHERE)).list();
			for (CompanyWarn cw : list) {
				switch(cw.getStreet_name()){
					case "新安":xinan++;break;
					case "西乡":xixiang++;break;
					case "福永":fuyong++;break;
					case "沙井":shajing++;break;
					case "松岗":songgang++;break;
					case "石岩":shiyan++;break;
				}
			}
		}
		obj.put("新安", xinan);
		obj.put("西乡", xixiang);
		obj.put("福永", fuyong);
		obj.put("沙井", shajing);
		obj.put("松岗", songgang);
		obj.put("石岩", shiyan);
		return obj;
	}
	@SuppressWarnings("unchecked")
	public JSONObject getDefuseStatusVol(String time,String street_name,String light_grade){
		JSONObject obj = new JSONObject();
		Session session = hs.getSessionFactory().openSession();
		if(street_name!=null&&street_name.equals("合计")){
			if(time!=null&&time.equals("0")){
				if(light_grade!=null&&light_grade.equals("合计")){
				String s = new SimpleDateFormat("yyyy").format(new Date());
				List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("status", "已化解"))
				.list();
				obj.put("已化解", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("status", "化解中"))
				.list();
				obj.put("化解中", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("status", "引发重劳"))
				.list();
				obj.put("引发重劳", list.size());
				}else if(light_grade!=null){
				String s = new SimpleDateFormat("yyyy").format(new Date());
				List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", light_grade))
				.add(Restrictions.eq("status", "已化解"))
				.list();
				obj.put("已化解", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", light_grade))
				.add(Restrictions.eq("status", "化解中"))
				.list();
				obj.put("化解中", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", light_grade))
				.add(Restrictions.eq("status", "引发重劳"))
				.list();
				obj.put("引发重劳", list.size());
				}
				
			}else if(time!=null){
			String s = "";
			if(time.length()==1){
				s = "-0"+time+"-";
			}else{
				s = "-"+time+"-";
			}
			if(light_grade!=null&&light_grade.equals("合计")){
			List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("status", "已化解"))
				.list();
				obj.put("已化解", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("status", "化解中"))
				.list();
				obj.put("化解中", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("status", "引发重劳"))
				.list();
				obj.put("引发重劳", list.size());
				}else if(light_grade!=null){
				List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", light_grade))
				.add(Restrictions.eq("status", "已化解"))
				.list();
				obj.put("已化解", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", light_grade))
				.add(Restrictions.eq("status", "化解中"))
				.list();
				obj.put("化解中", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", light_grade))
				.add(Restrictions.eq("status", "引发重劳"))
				.list();
				obj.put("引发重劳", list.size());
				}
		
			}//ef
		}else{
				if(time!=null&&time.equals("0")){
					
					String s = new SimpleDateFormat("yyyy").format(new Date());
					List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
					.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
					.add(Restrictions.eq("street_name", street_name))
					.add(Restrictions.eq("status", "已化解"))
					.list();
					obj.put("已化解", list.size());
					list = session.createCriteria(CompanyWarn.class)
					.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
					.add(Restrictions.eq("street_name", street_name))
					.add(Restrictions.eq("status", "化解中"))
					.list();
					obj.put("化解中", list.size());
					list = session.createCriteria(CompanyWarn.class)
					.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
					.add(Restrictions.eq("street_name", street_name))
					.add(Restrictions.eq("status", "引发重劳"))
					.list();
					obj.put("引发重劳", list.size());
					
				}else if(time!=null){
				String s = "";
				if(time.length()==1){
					s = "-0"+time+"-";
				}else{
					s = "-"+time+"-";
				}
				List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
					.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
					.add(Restrictions.eq("street_name", street_name))
					.add(Restrictions.eq("status", "已化解"))
					.list();
					obj.put("已化解", list.size());
					list = session.createCriteria(CompanyWarn.class)
					.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
					.add(Restrictions.eq("street_name", street_name))
					.add(Restrictions.eq("status", "化解中"))
					.list();
					obj.put("化解中", list.size());
					list = session.createCriteria(CompanyWarn.class)
					.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
					.add(Restrictions.eq("street_name", street_name))
					.add(Restrictions.eq("status", "引发重劳"))
					.list();
					obj.put("引发重劳", list.size());
			
			
				}//ef
		}
		
		
		return obj;
	}
	@SuppressWarnings("unchecked")
	public JSONObject getLightGradeVol(String time,String street_name){
		JSONObject obj = new JSONObject();
		Session session = hs.getSessionFactory().openSession();
		if(street_name!=null&&street_name.equals("合计")){
			if(time!=null&&time.equals("0")){
				
				String s = new SimpleDateFormat("yyyy").format(new Date());
				
				List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", "red"))
				.list();
				obj.put("红灯", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", "yellow"))
				.list();
				obj.put("黄灯", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", "blue"))
				.list();
				obj.put("蓝灯", list.size());
				list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
				.add(Restrictions.eq("light_grade", "green"))
				.list();
				obj.put("绿灯", list.size());
						
				
				
			}else if(time!=null){
			String s = "";
			if(time.length()==1){
				s = "-0"+time+"-";
			}else{
				s = "-"+time+"-";
			}
			
			List<CompanyWarn> list = session.createCriteria(CompanyWarn.class)
			.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
			.add(Restrictions.eq("street_name", street_name))
			.add(Restrictions.eq("light_grade", "red"))
			.list();
			obj.put("红灯", list.size());
			list = session.createCriteria(CompanyWarn.class)
			.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
			.add(Restrictions.eq("street_name", street_name))
			.add(Restrictions.eq("light_grade", "yellow"))
			.list();
			obj.put("黄灯", list.size());
			list = session.createCriteria(CompanyWarn.class)
			.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
			.add(Restrictions.eq("street_name", street_name))
			.add(Restrictions.eq("light_grade", "blue"))
			.list();
			obj.put("蓝灯", list.size());
			list = session.createCriteria(CompanyWarn.class)
			.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
			.add(Restrictions.eq("street_name", street_name))
			.add(Restrictions.eq("light_grade", "green"))
			.list();
			obj.put("绿灯", list.size());
		
			}//ef
		}
		return obj;
	}
	
	public JSONObject getTotalWarnVol(String street_name,String light_grade){
		JSONObject obj = new JSONObject();
		Session session = hs.getSessionFactory().openSession();
		String s = new SimpleDateFormat("MM").format(new Date());
		Integer m = Integer.valueOf(s);
		List list = null;
		if(street_name!=null&&street_name.equals("合计")){
			if(light_grade!=null&&light_grade.equals("合计")){
				for(Integer i = 1; i <= m; i++){
					if(i<10){
						s = "-0"+i.toString()+"-";
						list = session.createCriteria(CompanyWarn.class)
							.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
							.list();
						obj.put(i+"月", list==null?0:list.size());
					}else{
						s = "-"+i.toString()+"-";
						list = session.createCriteria(CompanyWarn.class)
								.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
								.list();
						obj.put(i+"月", list==null?0:list.size());
					}
					
				}
			}else{
				for(Integer i = 1; i <= m; i++){
					if(i<10){
						s = "-0"+i.toString()+"-";
						list = session.createCriteria(CompanyWarn.class)
							.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
							.add(Restrictions.eq("light_grade", light_grade))
							.list();
						obj.put(i+"月", list==null?0:list.size());
					}else{
						s = "-"+i.toString()+"-";
						list = session.createCriteria(CompanyWarn.class)
							.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
							.add(Restrictions.eq("light_grade", light_grade))
							.list();
						obj.put(i+"月", list==null?0:list.size());
					}
					
				}
				
			}
		}else{
			if(light_grade!=null&&light_grade.equals("合计")){
				for(Integer i = 1; i <= m; i++){
					if(i<10){
						s = "-0"+i.toString()+"-";
						list = session.createCriteria(CompanyWarn.class)
							.add(Restrictions.eq("street_name", street_name))
							.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
							.list();
						obj.put(i+"月", list==null?0:list.size());
					}else{
						s = "-"+i.toString()+"-";
						list = session.createCriteria(CompanyWarn.class)
								.add(Restrictions.eq("street_name", street_name))
								.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
								.list();
						obj.put(i+"月", list==null?0:list.size());
					}
					
				}
			}else{
				for(Integer i = 1; i <= m; i++){
					if(i<10){
						s = "-0"+i.toString()+"-";
						list = session.createCriteria(CompanyWarn.class)
							.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
							.add(Restrictions.eq("street_name", street_name))
							.add(Restrictions.eq("light_grade", light_grade))
							.list();
						obj.put(i+"月", list==null?0:list.size());
					}else{
						s = "-"+i.toString()+"-";
						list = session.createCriteria(CompanyWarn.class)
							.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
							.add(Restrictions.eq("street_name", street_name))
							.add(Restrictions.eq("light_grade", light_grade))
							.list();
						obj.put(i+"月", list==null?0:list.size());
					}
					
				}
				
			}
			
		}
		
		return obj;
	}
}
