package cn.sibat.warn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.sibat.warn.model.conduct.Conclusion;
import cn.sibat.warn.model.conduct.Inspection;
import cn.sibat.warn.serve.hib.dao.ProcessDao;
import net.sf.json.JSONObject;

@Controller
@Scope("session")
@RequestMapping("")
public class Process {
	@Autowired ProcessDao processDao;
	@RequestMapping(value="resolve_detail",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing getDufuseDetail(@RequestParam("time") String time,
			@RequestParam("status") String status){
		JSONObject objs = new JSONObject();
		JSONObject obj = processDao.getDufuseChart(time, status);
		objs.put("chart", obj);
		JSONArray array = processDao.getWarnCompanyDetail(time, status);
		objs.put("company", array);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(objs);
		return x;
	}
	
	
	
	@RequestMapping(value="inspection",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing getInspectionInfo(@RequestParam("company_id") String company_id){
		JSONObject objs = new JSONObject();
		Inspection in = processDao.getInspectionInfo(company_id);
		if(in!=null){
			objs.put("inspect_company", in.getCompany_id());
			objs.put("inspect_result", in.getResult());
			objs.put("inspect_detail", in.getDetail());
		}
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(objs);
		return x;
	}
	
	@RequestMapping(value="defuse",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing getDefuseInfo(@RequestParam("company_id") String company_id){
		List list = processDao.getDufuseInfo(company_id);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(list);
		return x;
	}
	
	@RequestMapping(value="result",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing getResultInfo(@RequestParam("company_id") String company_id){
		JSONObject obj = new JSONObject();
		Conclusion c = processDao.getConclusionInfo(company_id);
		obj.put("result", c.getResult());
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(obj);
		return x;
	}
	
	

}
