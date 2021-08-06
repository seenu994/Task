package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.admin.model.User;

public interface UserService {
//public User getUserByDetails(String loginName, String password, HttpServletResponse response) throws IOException;

User getUserByUsername(String lowerCase);


}