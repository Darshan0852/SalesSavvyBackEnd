package com.example.demo.services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.users;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
	

	private PasswordEncoder passwordEncoder;

	
	UserRepository userRepository;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		// TODO Auto-generated constructor stub
		this.userRepository = userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	public users Registration(users user) {
		if(userRepository.findByusername(user.getUsername()).isPresent()) {
			throw new RuntimeException("UserName is Already Present");
		}
		
		if(userRepository.findByemail(user.getEmail()).isPresent()) {
			throw new RuntimeException("UserEmial is Already Present");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	return	userRepository.save(user);
		
	}
}
