package com.example.demo.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class userController {

	UserService userService;

	public userController(UserService userService) {
		// TODO Auto-generated constructor stub
		this.userService = userService;

	}

	@PostMapping("/register")
	public ResponseEntity<?> Register(@RequestBody User user) {
		try {
	User register	=userService.registerUser(user);
	return ResponseEntity.ok(Map.of("Message", "Success","user", register));
	}catch (RuntimeException e) {
		// TODO: handle exception
		return ResponseEntity.badRequest().body(Map.of("Error",e.getMessage()));
	}
	}


}
