package com.xyram.ticketingTool.ticket.Contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.admin.model.User;
import com.xyram.ticketingTool.dao.AbstractDao;
import com.xyram.ticketingTool.enumType.UserRole;
import com.xyram.ticketingTool.service.UserService;
@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractDao{


  private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	//@Autowired
	//private OrganizationService organizationService;

	/**
	 * create new user
	 * 
	 * URL:http://<server name>/<context>/api/user/create
	 * 
	 * @param user
	 * @return
	 */
	@CrossOrigin
	@PostMapping("/createAdmin")
	public String createUser() {

		User user = userService.getUserByUsername("admin");
		
		if (user == null) {
			user = new User();
			user.setUsername("admin");
			 String encodedPassword = new BCryptPasswordEncoder().encode("admin");
			 user.setPassword(encodedPassword);
			user.setUserRole(UserRole.TICKETINGTOOL_ADMIN);

			getSession().saveOrUpdate(user);
		}
		return "success";	
		}

	/**
	 * update user by user object and id
	 * 
	 * URL:http://<server name>/<context>/api/user/update/32
	 * 
	 * @param user
	 * @param id
	 * @return
	 */
	/*
	 * @CrossOrigin
	 * 
	 * @PutMapping("/update/{id}") public String updateUser(@RequestBody User
	 * user, @PathVariable Integer id) { logger.info("updating user");
	 * user.setLoginName(user.getLoginName().toLowerCase()); return
	 * newUserService.updateUser(user, id); }
	 */
	/**
	 * get all users
	 * 
	 * URL:http://<server name>/<context>/api/user/all
	 * 
	 * @return
	 */
	/*
	 * @CrossOrigin
	 * 
	 * @GetMapping("/all") public List<User> getAllUser() { List<User> users= null;
	 * try { logger.info("fetching all users records"); users=
	 * newUserService.getAllUser(); } catch (Exception e) {
	 * logger.info("failed to fetching all users records"); e.printStackTrace(); }
	 * return users; }
	 */

	/**
	 * get user by id
	 * 
	 * URL:http://<server name>/<context>/api/user/7
	 * 
	 * @param id
	 * @return
	 */
	/*
	 * @CrossOrigin
	 * 
	 * @GetMapping("/{id}") public User getUserById(@PathVariable Integer id) {
	 * logger.info("fetching user by id"); return newUserService.getUserById(id); }
	 */

}

