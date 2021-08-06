package com.xyram.ticketingTool.ticket.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.dao.UserDao;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	private static Map<String, com.xyram.ticketingTool.admin.model.User> userCache = new HashMap<>();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.xyram.ticketingTool.admin.model.User user;

		if (userCache.containsKey(username)) {
			user = userCache.get(username);
		} else {
			user = userDao.loadUserByUsername(username);
			userCache.put(username, user);
		}
		return new User(username, user.getPassword(), new ArrayList<>());
	}

	
	
	  public com.xyram.ticketingTool.admin.model.User getAppUser(String username) {
	  
		  com.xyram.ticketingTool.admin.model.User user;
	  
	  if (userCache.containsKey(username)) 
	  { 
		  user = userCache.get(username);
	  userCache.remove(username); }
	  else { user =userDao.loadUserByUsername(username); }
	  
	  user.setPassword(null); return user; }
	 

}
