package com.xyram.ticketingTool.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.dao.AbstractDao;
import com.xyram.ticketingTool.dao.UserDao;

@Repository
public class UserDaoImpl extends AbstractDao implements UserDao {
	
	private final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	/**
	 * check for the presence of User in Database
	 */
	@Override
	public User loadUserByUsername(String username) {
		User user;
		try {
			user = (User) getSession().createQuery("FROM User u WHERE LOWER(u.username) = LOWER('" + username + "')")
					.uniqueResult();
			if (user != null) {
				logger.info("User details found for the username: " + username);
				return user;
			} else {
				logger.error("User details not found for username: " + username);
				throw new UsernameNotFoundException("User details not found for username: " + username);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public boolean verfiyUserPresence(User user) {
		boolean retval = false;
		User isUserLogin = null;
		try {
			isUserLogin = (User) getSession()
					.createQuery("FROM User u WHERE LOWER(u.username) = LOWER('" + user.getUsername() + "')").uniqueResult();
		} catch (Exception e) {
			logger.info("user verification failed",e.getMessage());
			e.printStackTrace();
		}
		if (isUserLogin != null) {
			retval = true;
		}
		return retval;
	}
	
	
	
	
	@Override
	public User getUserByUsername(String username) {
		User user=null;
		try {
			user = (User) getSession().createQuery("FROM User u WHERE LOWER(u.username) = LOWER('" + username + "')")
					.uniqueResult();
		}
		 catch (Exception e) {
			 logger.error("Error occured while fetching user"+ "Error message: "+e.getCause());
				throw e;
			}
			return user;
	}
}