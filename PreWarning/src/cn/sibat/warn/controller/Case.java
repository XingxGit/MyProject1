package cn.sibat.warn.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sibat.warn.model.cases.CaseUpload;
import cn.sibat.warn.safecheck.Auth;
import cn.sibat.warn.serve.hib.dao.CaseDao;

@Controller
@Scope("session")
@RequestMapping("")
public class Case {
	@Autowired Auth auth;
	@Autowired CaseDao caseDao;
	Logger log = Logger.getLogger(Case.class);
	@RequestMapping(value="upload_case",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing uploadCase(@RequestParam("company_id") String company_id,
			@RequestParam("kpi_ids") String kpi_ids,
			@RequestParam("agency") String agency,
			@RequestParam("user_id") String user_id,
			HttpSession session
			){
		Boolean sign = auth.checkUser(session);
		if(sign==false){
			log.info("not sign in but use upload_case");
			Xing x = new Xing();
			x.setSuccess(false);
			x.setMsg("对不起，请先登录！");
			return x;
		}
		log.info("execution upload_case api");
		CaseUpload cs = new CaseUpload();
		cs.setCompany_id(company_id);
		cs.setKpi_ids(kpi_ids);
		cs.setAgency(agency);
		cs.setUser_id(user_id);
		caseDao.saveCase(cs);

			Xing x = new Xing();
			x.setSuccess(true);
			x.setMsg("ok");
			return x;
			
	}
	
	@RequestMapping(value="search_case",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing searchCase(@RequestParam("company_id") String company_id,
			@RequestParam("agency") String agency,
			HttpSession session
			){
		Boolean sign = auth.checkUser(session);
		if(sign==false){
			log.info("not sign in but use search_case");
			Xing x = new Xing();
			x.setSuccess(false);
			x.setMsg("对不起，请先登录！");
			return x;
		}
		log.info("execution upload_case api");
		
		List list = caseDao.searchCase(company_id, agency);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(list);
		return x;
	}
	
	
	@RequestMapping(value="random_company",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing searchRandomCompany(HttpSession session){
		log.info("execution random_company api");
		
		List list = caseDao.searchRandomCompany();
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(list);
		return x;
	}
	
	
	
	@RequestMapping(value="search_company",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing searchCompany(@RequestParam("input") String input,HttpSession session){
		Boolean sign = auth.checkUser(session);
		if(sign==false){
			log.info("not sign in but use search_company");
			Xing x = new Xing();
			x.setSuccess(false);
			x.setMsg("对不起，请先登录！");
			return x;
		}
		log.info("execution search_company api");
		log.info("input is "+input);
		List list = caseDao.searchCompany(input);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(list);
		return x;
	}
	
	@RequestMapping(value="light_companyinfo",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing searchLightCompany(@RequestParam("light_grade") String light_grade,HttpSession session){
		Boolean sign = auth.checkUser(session);
		if(sign==false){
			log.info("not sign in but use light_companyinfo");
			Xing x = new Xing();
			x.setSuccess(false);
			x.setMsg("对不起，请先登录！");
			return x;
		}
		log.info("execution light_companyinfo api");
		List cwlist = caseDao.searchLightCase(light_grade);
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		x.setData(cwlist);
		return x;
	}

	
}
