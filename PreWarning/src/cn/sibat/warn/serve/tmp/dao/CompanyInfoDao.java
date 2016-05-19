package cn.sibat.warn.serve.tmp.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import cn.sibat.warn.model.company.CompanyInfo;
import cn.sibat.warn.model.company.CompanyKey;
import cn.sibat.warn.model.company.CompanyWarn;
import cn.sibat.warn.util.Config;
import cn.sibat.warn.util.HibSession;
import cn.sibat.warn.util.HibUtil;
import cn.sibat.warn.util.MD5Convernter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class CompanyInfoDao {
	@Autowired JdbcTemplate jdbcTemplate;
	@Autowired HibSession hs;
	@Autowired HibUtil hu;
	
	
	public CompanyInfo searchCompanyInfo(String company_id){
		Session session = hs.getSessionFactory().openSession();
		CompanyInfo ci = (CompanyInfo) session.createCriteria(CompanyInfo.class).add(Restrictions.eq("company_id", company_id)).uniqueResult();
		return ci;
	}
	
	public void saveCompanyInfo(CompanyInfo ci){
		hu.save(ci);
		
	}
	
	public List getCompanyInfo(List  list){
		List infoList = new ArrayList<>();
		return infoList;
	}
	
	@SuppressWarnings("unused")
	public String getCompanyKey(){
		
		Session session = hs.getSessionFactory().openSession();
//		 HibSession hb = HibSession.getInstance();
//		  Session session = hb.getSessionFactory().openSession();
		CompanyKey ck = (CompanyKey) session.createCriteria(CompanyKey.class).uniqueResult();
//		CompanyKey ck = new CompanyKey();
//		CompanyKey ck = null;
//		ck.setSkey("A1D30740134E4998C69B16C2796B9AAA");
		String cok="";
		if(ck!=null){
			System.out.println("ck is not null!");
			return ck.getSkey();
		}
		else{
			Config c = Config.getInstance();
			String sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//			String sd = "2016-05-13 17:59:39";
			StringBuilder s = new StringBuilder();
			s.append(c.getStringValue("c_username", "人力资源局"))
			.append(c.getStringValue("c_password", "xxzx-barlzy0506-sszt"))
			.append(sd);
			String key = "";
			try {
				key = MD5Convernter.calMd5(s.toString());
			} catch (Exception e) {
			}
			String pathUrl = c.getStringValue("c_url", "http://10.99.84.38:82/api/SsztService.asmx/GetDeptKey");
			String para = ("dept=164&"+"useKey="+key+"&invokingTime="+sd).toString();
			System.out.println(para);
			String re = this.httpUrlConnection(pathUrl, para);
			System.out.println(re);
			JSONObject obj = JSONObject.fromObject(re);
			CompanyKey cks = new CompanyKey();
			try {
				cks.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sd));
			} catch (ParseException e) {
			}
			cok = obj.getString("data");
			cks.setSkey(cok);
			hu.save(cks);
		}
			return cok;
	}
	
	public List listCompanyInfo() throws ParseException{
		Session session = hs.getSessionFactory().openSession();
//		Session session = HibSession.getInstance().getSessionFactory().openSession();
		List cinfoList = session.createCriteria(CompanyInfo.class).list();
		if(cinfoList!=null&&cinfoList.size()>0)
			return cinfoList;
		
		CompanyKey ck = (CompanyKey) session.createCriteria(CompanyKey.class).uniqueResult();
		String deptkey = "";
		if(ck!=null)
			deptkey = ck.getSkey();
		else
			deptkey = this.getCompanyKey();
		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String beginDate = this.getBeforeMonthAndYear(endDate);
		endDate = this.getBeforeYear(endDate);
		System.out.println("beginDate = "+beginDate+"endDate = "+endDate);
		String dept = "164";
		String ServiceCode = "jbxx";
		String para = ("dept="+dept+"&"+"deptkey="+deptkey+"&keyword="+"&ServiceCode="+ServiceCode+"&startDate="+beginDate+"&endDate="+endDate).toString();
		String url = "http://10.99.84.38:82/api/SsztService.asmx/GetSSZTSelect";
		String result = this.httpUrlConnection(url, para);
		System.out.println("list companyinfo result = "+result);
		JSONObject obj = JSONObject.fromObject(result);
		String status = obj.getString("status");
		if(status!=null&&status.equals("timeout")){
			session.beginTransaction();
			session.delete(session.createCriteria(CompanyKey.class).uniqueResult());
			session.getTransaction().commit();
			deptkey = this.getCompanyKey();
			para = ("dept="+dept+"&"+"deptkey="+deptkey+"&keyword="+"&ServiceCode="+ServiceCode+"&startDate="+beginDate+"&endDate="+endDate).toString();
			result = this.httpUrlConnection(url, para);
			obj = JSONObject.fromObject(result);
			JSONArray array = obj.getJSONArray("data");
			List tempList = null;
			if(array.size()>50)
				tempList = array.subList(0, 50);
			List clist = new ArrayList<>();
			for (Iterator iterator = tempList.iterator(); iterator.hasNext();) {
				JSONObject object = (JSONObject) iterator.next();
				CompanyInfo ci = new CompanyInfo();
				ci.setCompany_id(object.getString("zzjgdm"));
				ci.setCompany_name(object.getString("qymc"));
				ci.setCompany_address(object.getString("jycs"));
				ci.setStreet_name(this.getStreet(object.getString("jycs")));
				ci.setIndustry_name(object.getString("ssztlxmc"));
				session.clear();
				List list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("company_id", object.getString("zzjgdm"))).list();
				if(list!=null&&list.size()>0)
				ci.setIs_case("ture");
				else
				ci.setIs_case("false");
				clist.add(ci);
			}
			session.clear();
			List oldList = session.createCriteria(CompanyInfo.class).list();
			session.close();
			hu.batchDelete(oldList);
			hu.batchSave(clist);
			return clist;
		}
		if(status!=null&&status.equals("OK")){
		JSONArray array = obj.getJSONArray("data");
		List tempList = null;
		if(array.size()>50)
			tempList =  array.subList(0, 50);
		List clist = new ArrayList<>();
		for (Iterator iterator = tempList.iterator(); iterator.hasNext();) {
			JSONObject object = (JSONObject) iterator.next();
			CompanyInfo ci = new CompanyInfo();
			ci.setCompany_id(object.getString("zzjgdm"));
			ci.setCompany_name(object.getString("qymc"));
			ci.setCompany_address(object.getString("jycs"));
			ci.setStreet_name(this.getStreet(object.getString("jycs")));
			ci.setIndustry_name(object.getString("ssztlxmc"));
			session.clear();
			List list = session.createCriteria(CompanyWarn.class)
			.add(Restrictions.eq("company_id", object.getString("zzjgdm"))).list();
			if(list!=null&&list.size()>0)
			ci.setIs_case("ture");
			else
			ci.setIs_case("false");
			clist.add(ci);
		}
		session.clear();
		List oldList = session.createCriteria(CompanyInfo.class).list();
		session.close();
		hu.batchDelete(oldList);
		hu.batchSave(clist);
		return clist;
		}
		return null;
	}
	
	
	public List getCompanyInfo(String input) throws ParseException{
		Session session = hs.getSessionFactory().openSession();
		CompanyKey ck = (CompanyKey) session.createCriteria(CompanyKey.class).uniqueResult();
		String deptkey = "";
		if(ck!=null)
			deptkey = ck.getSkey();
		else
			deptkey = this.getCompanyKey();
		String dept = "164";
		String keyword = input;
		String ServiceCode = "jbxx";
		String para = ("dept="+dept+"&"+"deptkey="+deptkey+"&keyword="+keyword+"&ServiceCode="+ServiceCode+"&startDate=&endDate=").toString();
		String url = "http://10.99.84.38:82/api/SsztService.asmx/GetSSZTSelect";
		String result = this.httpUrlConnection(url, para);
		System.out.println("input result"+result);
		JSONObject obj = JSONObject.fromObject(result);
		String status = obj.getString("status");
		if(status!=null&&status.equals("timeout")){
			session.beginTransaction();
			session.delete(session.createCriteria(CompanyKey.class).uniqueResult());
			session.getTransaction().commit();
			session.close();
			deptkey = this.getCompanyKey();
			para = ("dept="+dept+"&"+"deptkey="+deptkey+"&keyword="+keyword+"&ServiceCode="+ServiceCode+"&startDate=&endDate=").toString();
			result = this.httpUrlConnection(url, para);
			obj = JSONObject.fromObject(result);
			JSONArray array = obj.getJSONArray("data");
			List clist = new ArrayList<>();
			for (Iterator iterator = array.iterator(); iterator.hasNext();) {
				JSONObject object = (JSONObject) iterator.next();
				CompanyInfo ci = new CompanyInfo();
				ci.setCompany_id(object.getString("zzjgdm"));
				ci.setCompany_name(object.getString("qymc"));
				ci.setCompany_address(object.getString("jycs"));
				ci.setStreet_name(this.getStreet(object.getString("jycs")));
				ci.setIndustry_name(object.getString("ssztlxmc"));
				session.clear();
				List list = session.createCriteria(CompanyWarn.class)
				.add(Restrictions.eq("company_id", object.getString("zzjgdm"))).list();
				if(list!=null&&list.size()>0)
				ci.setIs_case("ture");
				else
				ci.setIs_case("false");
				clist.add(ci);
			}
			return clist;
		}
		JSONArray array = obj.getJSONArray("data");
//		List tempList = null;
//		if(array.size()>50)
//			tempList = array.subList(0, 50);
		List clist = new ArrayList<>();
		for (Iterator iterator = array.iterator(); iterator.hasNext();) {
			JSONObject object = (JSONObject) iterator.next();
			CompanyInfo ci = new CompanyInfo();
			ci.setCompany_id(object.getString("zzjgdm"));
			ci.setCompany_name(object.getString("qymc"));
			ci.setCompany_address(object.getString("jycs"));
			ci.setStreet_name(this.getStreet(object.getString("jycs")));
			session.clear();
			List list = session.createCriteria(CompanyWarn.class)
			.add(Restrictions.eq("company_id", object.getString("zzjgdm"))).list();
			if(list!=null&&list.size()>0)
			ci.setIs_case("ture");
			else
			ci.setIs_case("false");
			clist.add(ci);
		}
		return clist;
		
	}
	
	private String httpUrlConnection(String pathUrl, String para) {  
		StringBuffer sb = new StringBuffer();
		try {   

		URL url = new URL(pathUrl);   
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();   
		  
		   
		// //设置连接属性   
		httpConn.setDoOutput(true);// 使用 URL 连接进行输出   
		httpConn.setDoInput(true);// 使用 URL 连接进行输入   
		httpConn.setUseCaches(false);// 忽略缓存   
		httpConn.setRequestMethod("POST");// 设置URL请求方法   
		String requestString = "WTM要以流方式发送到服务端的数据...";   
		  
		   
		// 设置请求属性   
		// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致   
		byte[] requestStringBytes = requestString.getBytes("UTF-8");   
		httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);   
		httpConn.setRequestProperty("Content-Type", "application/octet-stream");   
//		httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接   
		httpConn.setRequestProperty("Charset", "UTF-8");   
		//   

		
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		OutputStreamWriter osw = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");

		osw.write(para);

		osw.flush();

		osw.close();
	
		int responseCode = httpConn.getResponseCode();   
		  
		   
		if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功   
		// 当正确响应时处理数据   
		   
		String readLine;   
		BufferedReader responseReader;   
		// 处理响应流，必须与服务器响应流输出的编码一致   
		 responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));   
		while ((readLine = responseReader.readLine()) != null) {   
		sb.append(readLine).append("\n");   
		}   
		responseReader.close();   
//		tv.setText(sb.toString());   
		}   
		} catch (Exception ex) {   
		ex.printStackTrace();   
		}   
		  
		return sb.toString();
		}   
	
	
	public String getNextMonth(String time) throws ParseException{
		
		Calendar c = Calendar.getInstance();
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(time);
		c.setTime(d);
		c.add(Calendar.MONTH, 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
	
	public String getBeforeMonthAndYear(String time) throws ParseException{
		
		Calendar c = Calendar.getInstance();
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(time);
		c.setTime(d);
		c.add(Calendar.YEAR, -1);
		c.add(Calendar.DAY_OF_MONTH, -5);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
	
	public String getBeforeYear(String time) throws ParseException{
		
		Calendar c = Calendar.getInstance();
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(time);
		c.setTime(d);
		c.add(Calendar.YEAR, -1);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
	
	public String getBeforeFiveDay(String time) throws ParseException{
		Calendar c = Calendar.getInstance();
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(time);
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, -2);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
	
	public String getStreet(String dz){
		if(dz!=null){
			if(dz.contains("新安"))
				return "新安";
			if(dz.contains("西乡"))
				return "西乡";
			if(dz.contains("沙井"))
				return "沙井";
			if(dz.contains("福永"))
				return "福永";
			if(dz.contains("石岩"))
				return "石岩";
			if(dz.contains("松岗"))
				return "松岗";
		}
		return "";
	}
	
	public static void main(String[] args) throws ParseException {
		CompanyInfoDao cd = new CompanyInfoDao();
//		cd.listCompanyInfo();
		String dept = "164";
		String deptkey = "A754B46DED0BF25A5ED793A9351B3D76";
		String keyword = "44";
		String ServiceCode = "spgzxx";
		String url = "http://10.99.84.38:82/api/SsztService.asmx/GetSSZTSelect";
		String para = ("dept="+dept+"&"+"deptkey="+deptkey+"&keyword="+keyword+"&ServiceCode="+ServiceCode+"&startDate=&endDate=").toString();
		String result = cd.httpUrlConnection(url, para);
		System.out.println(result);
		
	}
}
