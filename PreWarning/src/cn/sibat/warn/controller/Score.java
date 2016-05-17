package cn.sibat.warn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.sibat.warn.model.cases.CaseUpload;
import cn.sibat.warn.safecheck.Auth;
import cn.sibat.warn.serve.hib.dao.CaseDao;
import net.sf.json.JSONObject;

@Controller
//@Scope("session")
@RequestMapping("")
public class Score {
	@Autowired Auth auth;
	@Autowired CaseDao caseDao;
	@RequestMapping(value="deduct_score_detail",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing listCompanyCaseKPI(@RequestParam("company_id") String company_id){
		List<CaseUpload> list = caseDao.searchCase(company_id);
		StringBuilder sb = new StringBuilder();
		for (CaseUpload cu : list) {
			sb.append(cu.getKpi_ids()).append("；");
		}
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(new JSONObject().put("kpi_ids", sb.toString()));
		return x;
	}
	
	@RequestMapping(value="deduct_score",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing listCompanyCase(@RequestParam("company_id") String company_id){
		List<CaseUpload> list = caseDao.searchCase(company_id);
		JSONArray array = new JSONArray();
		JSONObject objs = new JSONObject();
		for (CaseUpload cu : list) {
			JSONObject obj = new JSONObject();
			obj.put("agency", cu.getAgency());
			obj.put("user_name", auth.findUser(cu.getUser_id()).getName());
			obj.put("upload_time", cu.getUpload_time());
			array.add(obj);
		}
		objs.put("content", array);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(new JSONObject().put("kpi_ids", objs));
		return x;
	}

}
