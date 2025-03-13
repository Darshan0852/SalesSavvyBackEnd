package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.LoginDto;
import com.example.demo.entities.User;
import com.example.demo.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

	private  AuthService authService;

	public AuthController(AuthService authService) {
		// TODO Auto-generated constructor stub
		this.authService=authService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> Login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

		try {
			User user = authService.authenticate(loginDto.getUsername(), loginDto.getPassword());


			String token = authService.generateToken(user);
			Cookie cookie = new Cookie("authToken", token);
			cookie.setHttpOnly(true);
			cookie.setSecure(false);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			cookie.setDomain("localhost");
			response.addCookie(cookie);
			
//            response.addHeader("Set-Cookie",
//                    String.format("authToken=%s; HttpOnly; Path=/; Max-Age=3600; SameSite=None", token));

			Map<String, String> resposeBody = new HashMap<>();
			resposeBody.put("message", "Login successful");
			resposeBody.put("role", user.getRole().name());
			resposeBody.put("username", user.getUsername());
			return ResponseEntity.ok(resposeBody);

		} catch(Exception e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error" , e.getMessage()));
		}


	}
	
	 @PostMapping("/logout")
	    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request,HttpServletResponse response) {
	        try {
	        	User user=(User) request.getAttribute("authenticatedUser");
	            authService.logout(user);
	            Cookie cookie = new Cookie("authToken", null);
	            cookie.setHttpOnly(true);
	            cookie.setMaxAge(0);
	            cookie.setPath("/");
	            response.addCookie(cookie);
	            Map<String, String> responseBody = new HashMap<>();
	            responseBody.put("message", "Logout successful");
	            return ResponseEntity.ok(responseBody);
	        } catch (RuntimeException e) {
	            Map<String, String> errorResponse = new HashMap<>();
	            errorResponse.put("message", "Logout failed");
	            return ResponseEntity.status(500).body(errorResponse);
	        }
	    }
}
