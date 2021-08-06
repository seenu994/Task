package com.xyram.ticketingTool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.dao.UserDao;
import com.xyram.ticketingTool.service.UserService;

@Service
public class UserServiceImpl  implements UserService  {
    
	@Autowired
	UserDao userDao;
	
	@Override
	public User getUserByUsername(String username) {
	    User users =userDao.getUserByUsername(username);
		
	    return users;
	}

	
		}