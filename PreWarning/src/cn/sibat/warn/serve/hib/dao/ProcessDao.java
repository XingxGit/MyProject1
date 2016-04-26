package cn.sibat.warn.serve.hib.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

import cn.sibat.warn.json.util.JsonBuilder;
import cn.sibat.warn.json.util.JsonConcat;
import cn.sibat.warn.model.company.CompanyWarn;
import cn.sibat.warn.model.conduct.Conclusion;
import cn.sibat.warn.model.conduct.Defuse;
import cn.sibat.warn.model.conduct.Inspection;
import cn.sibat.warn.serve.tmp.dao.CompanyInfoDao;
import cn.sibat.warn.util.HibSession;
import net.sf.json.JSONObject;

@Service
public class ProcessDao {
	@Autowired HibSession hs;
	@Autowired JsonConcat jsonConcat;
	@Autowired JsonBuilder jsonBuilder;
	@Autowired CompanyInfoDao companyInfoDao;
	@SuppressWarnings("unchecked")
	public JSONObject getDufuseChart(String time,String status){
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
		.add(Restrictions.eq("status", status))
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
		.add(Restrictions.eq("status", status))
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
	
	public JSONArray getWarnCompanyDetail(String time,String status){
		JSONArray array = new JSONArray();
		Session session = hs.getSessionFactory().openSession();
		List warnList = null;
		if(time!=null&&time.equals("0")){
		warnList = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("status", status))
				.list();
		}else if(time!=null){
			String s = "";
			if(time.length()==1){
				s = "-0"+time+"-";
			}else{
				s = "-"+time+"-";
			}
			warnList = session.createCriteria(CompanyWarn.class)
			.add(Restrictions.like("time", s, MatchMode.ANYWHERE))
			.add(Restrictions.eq("status", status))
			.list();
		}
		List infoList = companyInfoDao.getCompanyInfo(warnList);
		array = JSONArray.parseArray(jsonConcat.concatList(warnList, infoList, "company_id"));
		return array;
	}
	
	public Inspection getInspectionInfo(String company_id){
		Inspection in = null;
		Session session = hs.getSessionFactory().openSession();
		in = (Inspection) session.createCriteria(Inspection.class)
				.add(Restrictions.eq("company_id", company_id))
				.uniqueResult();
		return in;
	}
	
	public List getDufuseInfo(String company_id){
		Session session = hs.getSessionFactory().openSession();
		List list = session.createCriteria(Defuse.class)
				.add(Restrictions.eq("company_id", company_id))
				.list();
		String s = jsonBuilder.append("defuse_time", null)
								.append("defuse_detail", "detail")
								.build(list);
		return JSONArray.parseArray(s);
	}
	
	public Conclusion getConclusionInfo(String company_id){
		Session session = hs.getSessionFactory().openSession();
		Conclusion c = (Conclusion) session.createCriteria(Conclusion.class)
				.add(Restrictions.eq("company_id", company_id))
				.uniqueResult();
		return c;
	}
}
