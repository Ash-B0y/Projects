package com.zoho.ars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zoho.ars.model.User;
import com.zoho.ars.service.RegistrationService;

@RestController
@PropertySource("classpath:configuration.properties")
public class RegistrationController {
	
	@Autowired
	RegistrationService registrationService;
	@Autowired
    private Environment environment;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user)
	{
		User userExists = registrationService.userExists(user);
		
		if(userExists==null)
		{
			User registeredUser = registrationService.registerUser(user);
			
			if(registeredUser==null)
				return new ResponseEntity<>(environment.getProperty("RegistrationService.ERROR"), HttpStatus.BAD_REQUEST);
			
			return new ResponseEntity<>(environment.getProperty("RegistrationService.SUCCESS"), HttpStatus.OK);
				
		}
		
			return new ResponseEntity<>(environment.getProperty("RegistrationService.USER_EXISTS"), HttpStatus.BAD_REQUEST);
		
		
	}

}
