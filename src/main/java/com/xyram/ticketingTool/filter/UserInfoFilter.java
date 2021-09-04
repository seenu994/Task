package com.xyram.ticketingTool.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.util.AuthConstants;


@Component
public class UserInfoFilter extends OncePerRequestFilter {
	
	@Autowired
	CurrentUser currentUser;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		currentUser.setName(request.getHeaders(AuthConstants.NAME).hasMoreElements()
				? request.getHeaders(AuthConstants.NAME).nextElement()
				: null);
		
		currentUser.setUserId(request.getHeaders(AuthConstants.USER_ID).hasMoreElements()
				? request.getHeaders(AuthConstants.USER_ID).nextElement()
				: null);
		currentUser.setUserRole(request.getHeaders(AuthConstants.USER_ROLE).hasMoreElements()
				? request.getHeaders(AuthConstants.USER_ROLE).nextElement()
				: null);
		

		filterChain.doFilter(request, response);
	}


}
