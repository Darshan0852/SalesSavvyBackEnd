package com.example.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dtos.LoginDto;
import com.example.demo.entities.users;
import com.example.demo.repositories.TokenRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class UserLoginService {

	UserRepository userRepo;
	TokenRepository tokenRepo;
	
	public UserLoginService(TokenRepository tokenRepo) {
		// TODO Auto-generated constructor stub
		this.tokenRepo = tokenRepo;
	}
	
	public String LoginService(LoginDto dto) {
		String name = dto.getUsername();
		String pass = dto.getPassword();
		

       Optional<users> user=userRepo.findByusername(name);
      return null;
	}
}
