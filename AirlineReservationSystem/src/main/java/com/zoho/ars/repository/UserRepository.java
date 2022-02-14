package com.zoho.ars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zoho.ars.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	

}
