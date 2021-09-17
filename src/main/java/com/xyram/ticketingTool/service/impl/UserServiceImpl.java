package com.xyram.ticketingTool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.service.UserService;

@Service
public class UserServiceImpl  implements UserService  {
    
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User getUserByUsername(String username) {
	    List<User> users =(List<User>) userRepository.findByUsername(username);
	    if(users!=null &&users.size()>0) {
	    	return users.get(0);
	    }
	    else {
	    return null;
	    }
	}

	@Override
	public int updateUID(String username, String uid) {

		
		return userRepository.updateUid(username, uid);

	}
}