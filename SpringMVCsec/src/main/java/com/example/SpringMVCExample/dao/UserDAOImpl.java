package com.example.SpringMVCExample.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.example.SpringMVCExample.model.Users;

@Repository
public class UserDAOImpl implements UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public void addUser(Users p) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("user saved successfully, Person Details="+p);
	}

	public void updateUser(Users p) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("user updated successfully, Person Details="+p);
	}
	@SuppressWarnings("unchecked")
	public List<Users> listUser() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<Users> personsList = session.createQuery("from Users").list();
		for(Users p : personsList){
			logger.info("USER List::"+p);
		}
		return personsList;
		//return null;
	}

	public Users getUserById(int id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();		
		Users p = (Users) session.load(Users.class, new Integer(id));
		logger.info("User loaded successfully, Person details="+p);
		return p;
		//return null;
	}

	public void removeUser(int id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Users p = (Users) session.load(Users.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("Person deleted successfully, person details="+p);
	}

}
