package cn.sibat.warn.safecheck;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sibat.warn.model.user.User;
import cn.sibat.warn.util.HibSession;
@Service
public class Auth {
	@Autowired HibSession hs;
	@SuppressWarnings("unchecked")
	public  User findUser(String name, String pwd){
		Session session = hs.getSessionFactory().openSession();
		List list = session.createCriteria(User.class)
		.add(Restrictions.eq("name", name))
		.add(Restrictions.eq("password", pwd))
		.list();
		
		if(list!=null&&list.size()>0){
			User user = (User) list.get(0);
			return user;
		}else{
			return null;
		}
	}
	
	public  User findUser(String user_id){
		Session session = hs.getSessionFactory().openSession();
		List list = session.createCriteria(User.class)
		.add(Restrictions.eq("user_id", user_id))
		.list();
		
		if(list!=null&&list.size()>0){
			User user = (User) list.get(0);
			return user;
		}else{
			return null;
		}
	}
	
	
	public  boolean checkUser(HttpSession session){
		User user = (User) session.getAttribute("userInfo");
		if(user!=null){
			try{
				user = findUser(user.getName(), user.getPassword());
			if(user!=null)
				return true;
			}catch(Exception e){
				Logger.getLogger(Auth.class).info("checkUser failed!");
			}
			
		}
		return false;
	}
	
	

}
