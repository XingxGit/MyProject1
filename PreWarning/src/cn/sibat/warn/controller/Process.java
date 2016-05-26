package cn.sibat.warn.controller;

import java.util.Date;
import java.util.List;

import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.sibat.warn.model.conduct.CaseInspection;
import cn.sibat.warn.model.conduct.Conclusion;
import cn.sibat.warn.model.conduct.Inspection;
import cn.sibat.warn.model.pending.InspectPending;
import cn.sibat.warn.model.pending.LightPending;
import cn.sibat.warn.model.user.User;
import cn.sibat.warn.safecheck.Auth;
import cn.sibat.warn.serve.hib.dao.CaseDao;
import cn.sibat.warn.serve.hib.dao.ProcessDao;
import cn.sibat.warn.socketServer.WebSocketTest;
import cn.sibat.warn.util.HibUtil;
import net.sf.json.JSONObject;

@Controller
//@Scope("session")
@RequestMapping("")
@ResponseBody
public class Process {
	@Autowired ProcessDao processDao;
	@Autowired Auth auth;
	@Autowired HibUtil hu;
	@Autowired CaseDao caseDao;
	Logger log = Logger.getLogger(Process.class);
	Session session;
	@RequestMapping(value="resolve_detail",produces="application/json;charset=UTF-8") 
	
	public Xing getDufuseDetail(@RequestParam("time") String time,
			@RequestParam("status") String status){
		log.info("use api resolve_detail");
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
	public Xing getInspectionInfo(@RequestParam("company_id") String company_id){
		log.info("use api inspection");
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
	public Xing getDefuseInfo(@RequestParam("company_id") String company_id){
		log.info("use api defuse");
		List list = processDao.getDufuseInfo(company_id);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(list);
		return x;
	}
	
	@RequestMapping(value="result",produces="application/json;charset=UTF-8") 
	public Xing getResultInfo(@RequestParam("company_id") String company_id){
		log.info("use api result");
		JSONObject obj = new JSONObject();
		Conclusion c = processDao.getConclusionInfo(company_id);
		obj.put("result", c.getResult());
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(obj);
		return x;
	}
	
	@RequestMapping(value="search_gridperson",produces="application/json;charset=UTF-8") 
	public Xing searchGrid(@RequestParam("street_name") String street_name){
		log.info("use api search_gridperson");
		JSONArray arrays = new JSONArray();
		JSONObject objs = new JSONObject();
		String group_name = "";
		List<User> list = auth.ListUser(street_name, "网格员");
		String user_id = "";
		String name = "";
		JSONArray array = new JSONArray();
		for (User u : list) {
			group_name = u.getGroup_name();
			if(u.getPrior()!=null&&u.getPrior()==1){
				user_id = u.getUser_id();
				name = u.getName();
				continue;
			}
				
			JSONObject obj = new JSONObject();
			obj.put("id", u.getUser_id());
			obj.put("name", u.getName());
			array.add(obj);
		}
		objs.put("组名", group_name);
		JSONObject o = new JSONObject();
		o.put("id", user_id);
		o.put("name", name);
		objs.put("组长", o);
		objs.put("组员", array);
		arrays.add(objs);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(arrays);
		return x;
	}
	
	
	@RequestMapping(value="assign_inspect",produces="application/json;charset=UTF-8") 
	public Xing assingInspect(@RequestParam("company_id") String company_id,
			@RequestParam("user_id") String user_id){
		log.info("use api assign_inspect");
		CaseInspection ci = new CaseInspection();
		ci.setCreate_time(new Date());
		ci.setCompany_id(company_id);
		ci.setUser_id(user_id);
		hu.save(ci);
		InspectPending ip = new InspectPending();
		ip.setCompany_id(company_id);
		ip.setUser_id(user_id);
		ip.setCreate_time(new Date());
		ip.setLight_grade(caseDao.searchWarnCompany(company_id).getLight_grade());
		hu.save(ip);
		WebSocketTest.onMessage(user_id+":assign inspect ", session);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(null);
		return x;
	}
	
	
	
	
	@RequestMapping(value="light_inspect",produces="application/json;charset=UTF-8") 
	public Xing lightInspect(@RequestParam("company_id") String company_id,
			@RequestParam("user_id") String user_id,
			@RequestParam("status") String status){
		log.info("use api light_inspect");
		InspectPending inp = processDao.searchInspectPendingByCid(company_id);
		hu.delete(inp);
		CaseInspection caseInspection = processDao.searchCaseInspection(company_id);
		caseInspection.setStatus(status);
		hu.update(caseInspection);
//		WebSocketTest.onMessage(user_id+":assign inspect ", session);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(null);
		return x;
	}
	
	@RequestMapping(value="edit_user",produces="application/json;charset=UTF-8") 
	public Xing editUser(
			@RequestParam("user_id") String user_id,
			@RequestParam("password") String password){
		log.info("use api edit_user");
		User u = auth.findUser(user_id);
		u.setPassword(password);
		hu.update(u);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(null);
		return x;
	}
	
}
