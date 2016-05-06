package cn.sibat.warn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.sibat.warn.model.cases.CaseOverTime;
import cn.sibat.warn.model.company.CompanyInfo;
import cn.sibat.warn.model.pending.InspectPending;
import cn.sibat.warn.model.pending.LightPending;
import cn.sibat.warn.serve.hib.dao.ProcessDao;
import cn.sibat.warn.serve.tmp.dao.CompanyInfoDao;
import net.sf.json.JSONObject;

@Controller
@Scope("session")
@RequestMapping("")
public class Bulletin {
	@Autowired ProcessDao processDao;
	@Autowired CompanyInfoDao companyInfoDao;
	
	@ResponseBody
	@RequestMapping(value="reform",produces="application/json;charset=UTF-8") 
	public Xing reform(@RequestParam("user_id")String user_id){
		List<LightPending> list = processDao.searchLightPending(user_id);
		JSONArray array = new JSONArray();
		for (LightPending lp : list) {
			JSONObject obj = new JSONObject();
			obj.put("reform_type", lp.getReform_type());
			obj.put("company_id", lp.getCompany_id());
			CompanyInfo ci = companyInfoDao.getCompanyInfo(lp.getCompany_id());
			obj.put("company_name", ci.getCompany_name());
			obj.put("company_address", ci.getCompany_address());
			array.add(obj);
		}
		
		List<InspectPending> lists = processDao.searchInspectPending(user_id);
		for (InspectPending lp : lists) {
			JSONObject obj = new JSONObject();
			obj.put("reform_type", lp.getReform_type());
			obj.put("company_id", lp.getCompany_id());
			CompanyInfo ci = companyInfoDao.getCompanyInfo(lp.getCompany_id());
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
	
	
	@ResponseBody
	@RequestMapping(value="notice",produces="application/json;charset=UTF-8") 
	public Xing notice(@RequestParam("street_name")String street_name){
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
	
}
