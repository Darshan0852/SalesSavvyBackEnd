package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.ForgetService;

@RestController
@RequestMapping("/api/forget")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class forgetPasswordController {

	@Autowired
	ForgetService forgetService;
	
	@PutMapping("/reset")
	public ResponseEntity<?> Reset(@RequestParam("email") String Email, @RequestParam("newPassword") String newPass){
		try {
	String res=	forgetService.passwordReset(Email,newPass);
	   
		return ResponseEntity.ok().body(res);}
		
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
