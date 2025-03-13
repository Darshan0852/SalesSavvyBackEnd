package com.example.demo.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@Service
public class ForgetService {

	@Autowired
	UserRepository repository;
	
	 private final  BCryptPasswordEncoder passwordEncoder;
	
	public ForgetService() {
		// TODO Auto-generated constructor stub
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public String passwordReset(String email,String pass) throws Exception{
		
		Optional<User> user =repository.findByEmail(email);
		if(!user.isPresent()) {
			 throw new IllegalArgumentException("Invalid Email");
		} else if(user.isPresent()) {
			User users =user.get();
			
			users.setPassword(passwordEncoder.encode(pass));
			repository.save(users);
			return "success";
		} else {
			return "Failed";
		}
	}
	
	
}
