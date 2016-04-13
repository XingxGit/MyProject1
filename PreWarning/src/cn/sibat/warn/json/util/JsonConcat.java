package cn.sibat.warn.json.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;

import cn.sibat.warn.model.company.CompanyInfo;
import net.sf.json.JSONObject;
/**
 * 
 * @author zhangxing
 * 该程序可以将两个list根据指定的key合成一个jsonarray
 * 版权所有，禁止非授权修改
 */
public class JsonConcat {
	
	public JSONArray concatList(List lo, List lt, Object key) {
		
		if(lo==null||lt==null||key==null)
			return null;
		if(lo.size()==0&&lt.size()==0)
			return null;
		JSONArray array = new JSONArray();
		try{
		if(key instanceof String){
			String realkey = key.toString();
			Map mapo = new HashMap<>();
			Map mapt = new HashMap<>();
			if(lo.size()<lt.size()){
				List temp = new ArrayList<>();
				temp = lt;
				lt = lo;
				lo = temp;
			}
			if(lo.size()>=lt.size()){
				Object obj = lo.get(0);
				Method m = obj.getClass().getMethod("get"+captureName(realkey));
				for (int i = 0; i < lo.size(); i++) {
					mapo.put(m.invoke(lo.get(i)), lo.get(i));
				}
				for (int i = 0; i < lt.size(); i++) {
					mapt.put(m.invoke(lt.get(i)), lt.get(i));
				}
				
				 for (Object okey : mapo.keySet()) {
					   JSONObject jobj = new JSONObject();
					   Object taro = mapo.get(okey);
					   Field[] fieldso = taro.getClass().getDeclaredFields();
					   for (int i = 0; i < fieldso.length; i++) {
						   if(fieldso[i].getName().equals("serialVersionUID"))continue;
						   Method mo = obj.getClass().getMethod("get"+captureName(fieldso[i].getName()));
						   if(!jobj.containsKey(fieldso[i].getName()))
						   jobj.put(fieldso[i].getName(), mo.invoke(taro));
					}
					   Object tart = mapt.get(okey);
					   if(tart!=null){
						   Field[] fieldst = tart.getClass().getDeclaredFields();
						   for (int i = 0; i < fieldst.length; i++) {
							   if(fieldst[i].getName().equals("serialVersionUID"))continue;
							   Method mt = obj.getClass().getMethod("get"+captureName(fieldst[i].getName()));
							   if(!jobj.containsKey(fieldst[i].getName()))
							   jobj.put(fieldst[i].getName(), mt.invoke(tart));
						}
					   }
					   array.add(jobj);
				}
			}
			
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return array;
		
	}
	
	 public static String captureName(String name) {
		 	name = name.substring(0, 1).toUpperCase() + name.substring(1);
	       return  name;
		        
		    }
	 
	 public static void main(String[] args) throws Exception {
		List list1 = new ArrayList<>();
		CompanyInfo c1  = new CompanyInfo();
		c1.setCompany_id("111");
		c1.setCompany_name("zhangxing");
		list1.add(c1);
		
		List list2 = new ArrayList<>();
		CompanyInfo c2  = new CompanyInfo();
		c2.setCompany_id("23");
		c2.setCompany_size("566");
		c2.setContacts_name("hello");
		list2.add(c2);
		c2  = new CompanyInfo();
		c2.setCompany_id("111");
		c2.setCompany_size("536");
		c2.setContacts_name("hep");
		list2.add(c2);
		
		String key = "company_id";
		JsonConcat jc = new JsonConcat();
		System.out.println(jc.concatList(list1, list2, key).toString());
		
		
	}

}
