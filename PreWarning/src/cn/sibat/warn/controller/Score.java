package cn.sibat.warn.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.sibat.warn.model.cases.CaseUpload;
import cn.sibat.warn.model.kpi.KPILightScore;
import cn.sibat.warn.safecheck.Auth;
import cn.sibat.warn.serve.hib.dao.CaseDao;
import cn.sibat.warn.serve.tmp.dao.CompanyInfoDao;
import net.sf.json.JSONObject;

@Controller
//@Scope("session")
@RequestMapping("")
@ResponseBody
public class Score {
	@Autowired Auth auth;
	@Autowired CaseDao caseDao;
	@Autowired CompanyInfoDao ciDao;
	Logger log = Logger.getLogger(Score.class);
	@RequestMapping(value="deduct_score_detail",produces="application/json;charset=UTF-8") 
	public Xing listCompanyCaseKPI(@RequestParam("company_id") String company_id){
		List<CaseUpload> list = caseDao.searchCase(company_id);
		StringBuilder sb = new StringBuilder();
		for (CaseUpload cu : list) {
			sb.append(cu.getKpi_ids()).append("；");
		}
		JSONObject obj = new JSONObject();
		obj.put("kpi_ids", sb.toString());
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(obj);
		return x;
	}
	
	@RequestMapping(value="deduct_score",produces="application/json;charset=UTF-8") 
	public Xing listCompanyCase(@RequestParam("company_id") String company_id){
		log.info(company_id);
		List<CaseUpload> list = caseDao.searchCase(company_id);
		log.info("list size = "+list.size());
		JSONArray array = new JSONArray();
		JSONObject objs = new JSONObject();
		for (CaseUpload cu : list) {
			JSONObject obj = new JSONObject();
			obj.put("agency", cu.getAgency());
			obj.put("user_id", cu.getUser_id());
			if(auth.findUser(cu.getUser_id())!=null)
			obj.put("user_name", auth.findUser(cu.getUser_id()).getName());
			obj.put("kpi_ids", cu.getKpi_ids());
			KPILightScore kls = caseDao.searchKpiLight(cu.getKpi_ids());
			if(kls!=null){
			obj.put("red", kls.getRed_score());
			obj.put("yellow", kls.getYellow_score());
			obj.put("blue", kls.getBlue_score());
			obj.put("green", kls.getGreen_score());
			}else{
				log.info("could not find kpi "+cu.getKpi_ids());
			}
			obj.put("upload_time", cu.getUpload_time());
			array.add(obj);
		}
		objs.put("content", array);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		JSONObject objss = new JSONObject();
		objss.put("kpi_ids", objs);
		x.setData(objss);
		return x;
	}

}
