package com.example.demo.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.users;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class userController {

	UserService userService;
	
	public userController(UserService userService) {
		// TODO Auto-generated constructor stub
		this.userService = userService;
			
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> Register(@RequestBody users user) {
		try {
	users register	=userService.Registration(user);
	return ResponseEntity.ok(Map.of("Message", "Success", "User", register));
	}catch (Exception e) {
		// TODO: handle exception
		return ResponseEntity.badRequest().body(Map.of("Error",e.getMessage()));
	}
	} 
}
