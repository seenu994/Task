package com.xyram.ticketingTool.ticket.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.UserRepository;
import com.xyram.ticketingTool.enumType.UserStatus;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private static Map<String, com.xyram.ticketingTool.admin.model.User> userCache = new HashMap<>();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.xyram.ticketingTool.admin.model.User user = null;

		if (userCache.containsKey(username)) {
			user = userCache.get(username);
		} else {
			List<com.xyram.ticketingTool.admin.model.User> users = userRepository.findByUsername(username);
			if (users != null && users.size() > 0) {
				user = users.get(0);
				userCache.put(username, users.get(0));
			}
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
		/*
		 * System.out.println(user.getStatus()); if(user.getStatus()==UserStatus.ACTIVE)
		 * {
		 */
		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
		}
		/*
		 * else { return new User(user.getUsername(), user.getPassword(), false, true,
		 * true, true, grantedAuthorities); }
		 */
	
	
//		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found for the username: " + username);
//		return new User(username, user.getPassword(), new ArrayList<>());



	public com.xyram.ticketingTool.admin.model.User getAppUser(String username) {

		com.xyram.ticketingTool.admin.model.User user = null;

		if (userCache.containsKey(username)) {
			user = userCache.get(username);
			userCache.remove(username);
		} else {
			List<com.xyram.ticketingTool.admin.model.User> users = userRepository.findByUsername(username);
			if (users != null && users.size() > 0) {
				user = users.get(0);
			}
		}

		user.setPassword(null);
		return user;
	}

}
