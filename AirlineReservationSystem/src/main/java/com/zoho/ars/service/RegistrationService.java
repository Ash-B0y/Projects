package com.zoho.ars.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoho.ars.model.User;
import com.zoho.ars.repository.UserRepository;

@Service
public class RegistrationService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User userExists(User user)
	{
		Optional<User> userExists = userRepository.findById(user.getUserId());
		
		 if(!userExists.isPresent())
			 return null;
		 
		 return userExists.get(); 
		
		
	}
	
	public User registerUser(User user) {
		
		
			 userRepository.save(user);
			 Optional<User> persistedUser = userRepository.findById(user.getUserId());
				
			 if(!persistedUser.isPresent())
				 return null;
			 
			 return persistedUser.get();
			 
		 }
		 
		 		
		 
	}


