package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.LoginDto;
import com.example.demo.entities.users;
import com.example.demo.services.LoginAuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

	private  LoginAuthService authService;
	
	public AuthController(LoginAuthService authService) {
		// TODO Auto-generated constructor stub
		this.authService=authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> Login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
		
		try {
			users user = authService.LoginService(loginDto);
			
			
			String token = authService.generateToken(user);
			
			Cookie cookie = new Cookie("authToken", token);
			cookie.setHttpOnly(true);
			cookie.setSecure(false);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			response.addCookie(cookie);
			
			Map<String, String> resposeBody = new HashMap<>();
			resposeBody.put("message", "Login successful");
			resposeBody.put("role", user.getRole().name());
			return ResponseEntity.ok(resposeBody);
			
		} catch(Exception e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error" , e.getMessage()));
		}
		
		
	}
}
