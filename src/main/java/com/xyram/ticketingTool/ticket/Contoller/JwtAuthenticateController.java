package com.xyram.ticketingTool.ticket.Contoller;

import org.slf4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.ticket.Model.JwtRequest;
import com.xyram.ticketingTool.ticket.Model.JwtResponse;
import com.xyram.ticketingTool.ticket.Service.JwtUserDetailsService;
import com.xyram.ticketingTool.ticket.config.JwtTokenUtil;
import com.xyram.ticketingTool.util.AuthUtil;

@SuppressWarnings("unused")
@RestController
@CrossOrigin
public class JwtAuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		User user = new User();

		final User userDetails = userDetailsService.getAppUser(authenticationRequest.getUsername().toLowerCase());
		System.out.println(userDetails);
		final String token = jwtTokenUtil.generateToken(userDetails);
		User appUser = userDetailsService.getAppUser(authenticationRequest.getUsername());
		JwtResponse response = new JwtResponse(token, "sessionId",
				AuthUtil.getBaseResourcePath(userDetails.getUserRole().toString()),userDetails);

		return ResponseEntity.ok(response);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			// logger.info("login successful for the username: "+username);
		} catch (DisabledException e) {
			// logger.error("DisabledException for login attempt for the username:
			// "+username);
			 throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive for the username: "+username);
		} catch (BadCredentialsException e) {
			// logger.error("BadCredentialsException for login attempt for the username:
			// "+username);
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
