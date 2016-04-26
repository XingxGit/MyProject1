package cn.sibat.warn.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sibat.warn.model.cases.CaseUpload;
import cn.sibat.warn.model.user.User;
import cn.sibat.warn.safecheck.Auth;
import cn.sibat.warn.serve.hib.dao.CaseDao;
import cn.sibat.warn.service.AlgoExcutorService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
//@Scope("session")
@RequestMapping("")
public class Case {
	@Autowired JdbcTemplate jdbcTemplate;
	@Autowired Auth auth;
	@Autowired CaseDao caseDao;
	Logger log = Logger.getLogger(Case.class);
	@RequestMapping(value="upload_case",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing uploadCase(
//			@RequestParam("company_id") String company_id,
//			@RequestParam("kpi_ids") String kpi_ids,
//			@RequestParam("agency") String agency,
//			@RequestParam("value") String value,
//			@RequestParam("user_id") String user_id,
			@RequestParam("content") String content
//			HttpSession session
			){
		Set<String> set = new HashSet<>();
		/*Boolean sign = auth.checkUser(session);
		if(sign==false){
			log.info("not sign in but use upload_case");
			Xing x = new Xing();
			x.setSuccess(false);
			x.setMsg("对不起，请先登录！");
			return x;
		}*/
		log.info("execution upload_case api");
		JSONArray array = JSONArray.fromObject(content);
		List list = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			CaseUpload cs = new CaseUpload();
			if(obj.containsKey("company_id")&&obj.containsKey("kpi_ids")){
				System.out.println(obj.containsKey("company_id"));
				set.add(obj.getString("company_id"));
				cs.setCompany_id(obj.getString("company_id"));
				cs.setKpi_ids(obj.getString("kpi_ids"));
				CaseUpload cp = caseDao.searchCaseByIds(obj.getString("company_id"), obj.getString("kpi_ids"));
				if(cp!=null){
					cp.setValue(obj.containsKey("value")==true?cp.getValue():Double.valueOf(obj.getString("value")));
				caseDao.updateCase(cp);
				continue;
				}
			}
			if(obj.containsKey("agency"))
				cs.setAgency(obj.getString("agency"));
			if(obj.containsKey("user_id")){
				cs.setUser_id(obj.getString("user_id"));
				User user = auth.findUser(obj.getString("user_id"));
				if(user!=null)
					cs.setPrior(user.getPrior());
			}
			if(obj.containsKey("value"))
				cs.setValue(Double.valueOf(obj.getString("value")));
			list.add(cs);
		}
			caseDao.saveListCase(list);
			AlgoExcutorService algo = AlgoExcutorService.getInstance();
			for (String s : set) {
				algo.getBlockQueue().offer(s);
			}
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
	public Xing searchLightCompanyInfo(@RequestParam("light_grade") String light_grade,HttpSession session){
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
	
	@RequestMapping(value="jdbc",produces="application/json;charset=UTF-8") 
	@ResponseBody
	public Xing searchCompanyInfo(@RequestParam("company_id") String company_id,HttpSession session){
		
		log.info("execution light_companyinfo api");
		List list = jdbcTemplate.queryForList("select * from case_upload where company_id = ?", new Object[]{company_id});
		System.out.println(list.size()+"-----------");
		Xing x = new Xing();
		x.setSuccess(true);
		x.setMsg("ok");
		return x;
	}

	
}
