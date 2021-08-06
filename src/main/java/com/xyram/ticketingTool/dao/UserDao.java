package com.xyram.ticketingTool.dao;

import com.xyram.ticketingTool.admin.model.User;

public interface UserDao {
	//public User getUserByDetails(String loginName, String password, HttpServletResponse response) throws IOException;

	//User loadUserByUsername(String username);

	//public User loadUserByUsername(String username);

	//public List<User> findByUsername(String username);

	public  boolean verfiyUserPresence(User user) ;
		// TODO Auto-generated method stub
		//return false;

	 User loadUserByUsername(String username);

	User getUserByUsername(String username);

	

	}


