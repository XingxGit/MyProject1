package cn.sibat.warn.util;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibUtil {
	@Autowired HibSession hs;
	public void save(Object obj) {
		Session session = hs.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.save(obj);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}
		session.close();
	}
	
	public void delete(Object obj) {
		Session session = hs.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.delete(obj);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}
		session.close();
	}

	public void update(Object obj) {
		Session session = hs.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.update(obj);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}
		session.close();
	}
	
	public boolean batchDelete( List list ){
		if( list == null || list.isEmpty() ){
			return false;
		}
		Session session = hs.getSessionFactory().openSession();
		try{
			session.beginTransaction();
		Iterator iter = list.iterator();
		while( iter.hasNext() ){
			session.delete( iter.next() );
		}
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}
		session.close();
		return true;
	}
	
	public boolean batchSave( List list ){
		if( list == null || list.isEmpty() ){
			return false;
		}
		Session session = hs.getSessionFactory().openSession();
		try{
			session.beginTransaction();
		Iterator iter = list.iterator();
		while( iter.hasNext() ){
			session.save( iter.next() );
		}
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}
		session.close();
		return true;
	}
}
