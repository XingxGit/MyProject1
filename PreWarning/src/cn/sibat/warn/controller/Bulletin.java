package cn.sibat.warn.controller;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.sibat.warn.model.cases.CaseOverTime;
import cn.sibat.warn.model.company.CompanyInfo;
import cn.sibat.warn.model.company.CompanyWarn;
import cn.sibat.warn.model.pending.InspectPending;
import cn.sibat.warn.model.pending.LightPending;
import cn.sibat.warn.serve.hib.dao.CaseDao;
import cn.sibat.warn.serve.hib.dao.ProcessDao;
import cn.sibat.warn.serve.tmp.dao.CompanyInfoDao;
import net.sf.json.JSONObject;

@Controller
//@Scope("session")
@RequestMapping("")
@ResponseBody
public class Bulletin {
	@Autowired ProcessDao processDao;
	@Autowired CompanyInfoDao companyInfoDao;
	@Autowired CaseDao caseDao;
	Logger log = Logger.getLogger(Bulletin.class);
	
	@RequestMapping(value="reform",produces="application/json;charset=UTF-8") 
	public Xing reform(@RequestParam("user_id")String user_id) throws ParseException{
		log.info("use api reform");
		List<LightPending> list = processDao.searchLightPending(user_id);
		JSONArray array = new JSONArray();
		for (LightPending lp : list) {
			JSONObject obj = new JSONObject();
			obj.put("reform_type", lp.getReform_type());
			obj.put("company_id", lp.getCompany_id());
			obj.put("light_grade", lp.getLight_grade());
			CompanyWarn wc = caseDao.searchWarnCompany(lp.getCompany_id());
			if(wc==null)continue;
			obj.put("company_name", wc.getCompany_name());
			obj.put("company_address", wc.getCompany_address());
			array.add(obj);
		}
		
		List<InspectPending> lists = processDao.searchInspectPending(user_id);
		for (InspectPending lp : lists) {
			JSONObject obj = new JSONObject();
			obj.put("reform_type", lp.getReform_type());
			obj.put("company_id", lp.getCompany_id());
			CompanyInfo ci = (CompanyInfo) companyInfoDao.getCompanyInfo(lp.getCompany_id()).get(0);
			obj.put("company_name", ci.getCompany_name());
			obj.put("company_address", ci.getCompany_address());
			array.add(obj);
		}
		
		
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(array);
		return x;
	}
	
	

	@RequestMapping(value="notice",produces="application/json;charset=UTF-8") 
	public Xing notice(@RequestParam("street_name")String street_name){
		log.info("use api notice");
		List<CaseOverTime> list = processDao.searchOverTimeCase(street_name);
		int red = 0;
		int yellow = 0;
		int blue = 0;
		int green = 0;
		for (CaseOverTime ct : list) {
			switch(ct.getLight_grade()){
			case "red":red++;break;
			case "yellow":yellow++;break;
			case "blue":blue++;break;
			case "green":green++;break;
			}
		}
		
		JSONObject obj = new JSONObject();
		obj.put("red_timeout", red);
		obj.put("yellow_timeout", yellow);
		obj.put("blue_timeout", blue);
		obj.put("green_timeout", green);
		
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(obj);
		return x;
	}
	
	
	@ResponseBody
	@RequestMapping(value="notice_detail_timeout",produces="application/json;charset=UTF-8") 
	public Xing notice_detail(@RequestParam("street_name")String street_name){
		log.info("use api notice_detail_timeout");
		List<CaseOverTime> list = processDao.searchOverTimeCase(street_name);
		JSONArray array = new JSONArray();
		for (CaseOverTime ct : list) {
			JSONObject obj = new JSONObject();
			obj.put("light_grade", ct.getLight_grade());
			obj.put("company_id", ct.getCompany_id());
			obj.put("company_name", caseDao.searchWarnCompany(ct.getCompany_id()).getCompany_name());
			obj.put("reason", ct.getOvertime_type());
			array.add(obj);
		}
		JSONObject objs = new JSONObject();
		objs.put("content", array);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(objs);
		return x;
	}
	
}
