package cn.sibat.warn.json.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.sibat.warn.model.user.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author zhangxing
 *	本工具类可以将任意给定model按照指定的字段输出成json
 *	版权所有，禁止非授权修改
 */
public class JsonBuilder {
	Object obj;
	List list;
	Map map = new HashMap<>();
	Logger log = Logger.getLogger(JsonBuilder.class);
	 JsonBuilder() {
		super();
	}
	public JsonBuilder(Object obj) {
		super();
		this.obj = obj;
	}

	public JsonBuilder(List list) {
		super();
		this.list = list;
	}


	public JsonBuilder append(String s1, String s2) {
		if (s1 != null)
			map.put(s1, s2);
		return this;
	}

	public JSONObject buildObject() {
		JSONObject jobj = new JSONObject();
		for (Object okey : map.keySet()) {
			Object tkey = map.get(okey);
			try{
				Method m = obj.getClass().getMethod("get"+captureName(okey.toString()));
				if(tkey!=null){
					jobj.put(tkey, m.invoke(obj));
				}else{
					jobj.put(okey, m.invoke(obj));
				}
					
			}catch(Exception e){
				log.info("no such method "+"get"+captureName(okey.toString()));
			}
		}
		return jobj;
	}
	
	public JSONArray buildList(){
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Object aobj = list.get(i);
			JSONObject jobj = new JSONObject();
			for (Object okey : map.keySet()) {
				Object tkey = map.get(okey);
				try{
					Method m = aobj.getClass().getMethod("get"+captureName(okey.toString()));
					if(tkey!=null){
						jobj.put(tkey, m.invoke(aobj));
					}else{
						jobj.put(okey, m.invoke(aobj));
					}
						
				}catch(Exception e){
					log.info("no such method "+"get"+captureName(okey.toString()));
				}
			}
			array.add(jobj);
		}
		
		return array;
	}
	
	
	
	public static String captureName(String name) {
	 	name = name.substring(0, 1).toUpperCase() + name.substring(1);
       return  name;
	        
	    }
	@Test
	public static void main(String[] args) throws Exception {
		List list = new ArrayList<>();
		User user = new User();
		user.setName("zhangxing");
		user.setPassword("123");
		
		User user2 = new User();
		user2.setName("xingxing");
		user2.setPassword("456");
		user2.setRank("buzhang");
		JsonBuilder jb = new JsonBuilder(user);
		System.out.println(jb.append("name", null).append("password", "pwd").buildObject());
		list.add(user);
		list.add(user2);
		jb = new JsonBuilder(list);
		System.out.println(jb.append("name", null).append("password", "pwd").append("rank", null).buildList());
	}

}
