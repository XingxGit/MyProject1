package cn.sibat.warn.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
