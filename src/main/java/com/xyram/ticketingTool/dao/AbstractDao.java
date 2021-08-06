package com.xyram.ticketingTool.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xyram.ticketingTool.baseData.model.IBaseData;

public abstract class AbstractDao {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		Session session;

try {
    session = sessionFactory.getCurrentSession();
} catch (HibernateException e) {
    session = sessionFactory.openSession();
}
return session;
	}

	public void persist(Object entity) {
		getSession().persist(entity);
	}

	public void delete(Object entity) {
		getSession().delete(entity);
	}

	public boolean updateSectionInfo(IBaseData labinfo) {
		// TODO Auto-generated method stub
		return false;
	}
}

