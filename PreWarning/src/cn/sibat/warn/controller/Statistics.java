package cn.sibat.warn.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sibat.warn.safecheck.Auth;
import cn.sibat.warn.serve.hib.dao.StatisticsDao;
import net.sf.json.JSONObject;

@Controller
@Scope("session")
@RequestMapping("")
public class Statistics {
	Logger log = Logger.getLogger(Statistics.class);
	@Autowired StatisticsDao statisticsDao;
	@RequestMapping(value="light_statistics",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing getInfo(HttpSession session){
		log.info("execution light_statistics api");
		JSONObject obj = statisticsDao.getCompanyLightsVol();
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(obj);
		return x;
	}
	
	@RequestMapping(value="search_charts",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing getCharts(HttpSession session,
			@RequestParam("time") String time,
			@RequestParam("street_name") String street_name,
			@RequestParam("light_grade") String light_grade){
		log.info("execution search_charts api");
		JSONObject objs = new JSONObject();
		JSONObject obj = statisticsDao.getCompanyLightsVol();
		objs.put("chart1", obj);
		obj = statisticsDao.getDefuseStatusVol(time, street_name,light_grade);
		objs.put("chart2", obj);
		obj = statisticsDao.getLightGradeVol(time, street_name);
		objs.put("chart3", obj);
		obj = statisticsDao.getTotalWarnVol(street_name, light_grade);
		objs.put("chart4", obj);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(objs);
		return x;
	}
	

}
