package com.zoho.ars.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zoho.ars.model.Login;
import com.zoho.ars.model.User;
import com.zoho.ars.service.LoginService;

@RestController
@PropertySource("classpath:configuration.properties")
public class LoginController {
	@Autowired
	Login login;
	@Autowired
	LoginService loginService;
	@Autowired
    private Environment environment;
	
	@GetMapping(value = "/authenticateLogin")
	public ResponseEntity<String> authenticateLogin(@RequestParam(name="userId", required = true) String userId,@RequestParam(name="password", required = true) String password,HttpServletRequest request)
	{
		if(request.getSession().getAttribute("Logged-In-User")!=null)
			return new ResponseEntity<>(environment.getProperty("LoginService.USER_ALREADY_LOGGED_IN"), HttpStatus.OK);
				
		login.setUserName(userId);
		login.setPassword(password);
		User user = loginService.authenticateLogin(login);
		if(user==null)
			return new ResponseEntity<>(environment.getProperty("LoginService.INVALID_CREDENTIALS"), HttpStatus.NOT_FOUND);
		request.getSession().setAttribute("Logged-In-User", user.getUserId());
		return new ResponseEntity<>(environment.getProperty("LoginService.VALID_USER"), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/logOut")
	public ResponseEntity<String> logOut(HttpServletRequest request)
	{
		if(request.getSession().getAttribute("Logged-In-User")==null)
			return new ResponseEntity<>(environment.getProperty("LoginService.SIGN_OUT_NIL"), HttpStatus.OK);
		
		String message = "The user with the following Id : "+request.getSession().getAttribute("Logged-In-User")+" has ";
		request.getSession().setAttribute("Logged-In-User", null);
		return new ResponseEntity<>(message+environment.getProperty("LoginService.SIGN_OUT"), HttpStatus.OK);
		
	}
	
}
