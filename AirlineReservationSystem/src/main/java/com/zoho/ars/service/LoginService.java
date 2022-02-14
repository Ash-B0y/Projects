package com.zoho.ars.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoho.ars.model.Login;
import com.zoho.ars.model.User;
import com.zoho.ars.repository.UserRepository;

@Service
public class LoginService{
	
	@Autowired
	private UserRepository userRepository;
	
	
public User authenticateLogin(Login userLogin) {
	
	
	Optional<User> user = userRepository.findById(userLogin.getUserName());
	
	 if(!user.isPresent())
		 return null;
	 
	 
	 else if(!(user.get().getPassword().equals(userLogin.getPassword())))
		 return null;
	 
	 return user.get();
}

}
