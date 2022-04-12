package com.xyram.ticketingTool.filter;

import org.springframework.stereotype.Component;


@Component
public class UserInfoFilter  {
	
//	@Autowired
//	CurrentUser currentUser;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
//		currentUser.setName(request.getHeaders(AuthConstants.NAME).hasMoreElements()
//				? request.getHeaders(AuthConstants.NAME).nextElement()
//				: null);
//		
//		currentUser.setUserId(request.getHeaders(AuthConstants.USER_ID).hasMoreElements()
//				? request.getHeaders(AuthConstants.USER_ID).nextElement()
//				: null);
//		currentUser.setUserRole(request.getHeaders(AuthConstants.USER_ROLE).hasMoreElements()
//				? request.getHeaders(AuthConstants.USER_ROLE).nextElement()
//				: null);
//		
//
//		filterChain.doFilter(request,response);
//	}
//

}
