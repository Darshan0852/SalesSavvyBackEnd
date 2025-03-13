package com.example.demo.AdminServices;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repositories.JWTTokenRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class AdminUserService {
	
	UserRepository userRepository;
	
	JWTTokenRepository jwtTokenRepository;
	
	public AdminUserService(UserRepository userRepository,JWTTokenRepository jwtTokenRepository) {
		// TODO Auto-generated constructor stub
		this.jwtTokenRepository=jwtTokenRepository;
		this.userRepository=userRepository;
	}

	public User getUserById(Integer userId) {
		return userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("User not found"));
	}
	
	@Transactional
	public User modifyUser(Integer userId, String username, String email, String role) {
		
     	 Optional<User> userOptional =userRepository.findById(userId);
     	 if(userOptional.isEmpty()) {
     		 throw new IllegalArgumentException("User not found");
     	 }
     	 
     	 User existingUser = userOptional.get();
     	 
     	 if(username != null && !username.isEmpty()) {
     		 existingUser.setUsername(username);
     	 }
     	 if(email != null && email.isEmpty()) {
     		 existingUser.setEmail(email);
     	 }
     	 if(role != null && role.isEmpty()) {
     		 
     		 try {
     			 existingUser.setRole(Role.valueOf(role));
     			 }catch (Exception e) {
				// TODO: handle exception
     			 throw new IllegalArgumentException("Invalid role: "+role);
			}
     	 }
     	 jwtTokenRepository.deleteByUserId(userId);
     	 return userRepository.save(existingUser);
	}
}
