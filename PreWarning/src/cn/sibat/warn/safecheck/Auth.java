package cn.sibat.warn.safecheck;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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
		ProjectionList proList = Projections.projectionList();
        proList.add(Projections.alias(Projections.groupProperty("user_id"), "user_id"));
        proList.add(Projections.alias(Projections.groupProperty("rank"), "rank"));
        proList.add(Projections.alias(Projections.groupProperty("agency"), "agency"));
        proList.add(Projections.alias(Projections.groupProperty("street_name"), "street_name"));
        User user  =  (User) session.createCriteria(User.class)
		.add(Restrictions.eq("name", name))
		.add(Restrictions.eq("password", pwd))
		.setProjection(proList)
		.setResultTransformer(Transformers.aliasToBean(User.class)) 
		.uniqueResult();
		return user;
	}
	
	public  User findUser(String user_id){
		Session session = hs.getSessionFactory().openSession();
		User user = (User) session.createCriteria(User.class)
		.add(Restrictions.eq("user_id", user_id))
		.uniqueResult();
		return user;
	}
	
	public List ListUser(String street_name, String rank){
		Session session = hs.getSessionFactory().openSession();
		List list =  session.createCriteria(User.class)
		.add(Restrictions.eq("street_name", street_name))
		.add(Restrictions.eq("rank", rank))
		.list();
		return list;
		
		
		
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
