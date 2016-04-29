package cn.sibat.warn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;

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
	public Xing reform(String user_id){
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
}
