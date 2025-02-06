package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.LoginDto;
import com.example.demo.entities.jwt_tokens;
import com.example.demo.entities.users;
import com.example.demo.repositories.TokenRepository;
import com.example.demo.repositories.UserRepository;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class LoginAuthService {

	UserRepository userRepo;
	TokenRepository tokenRepo;
	private PasswordEncoder passwordEncoder;
	private static SecretKey key=Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	public LoginAuthService(TokenRepository tokenRepo,UserRepository userRepo,PasswordEncoder passwordEncoder) {
		// TODO Auto-generated constructor stub
		this.tokenRepo = tokenRepo;
		this.userRepo=userRepo;
		this.passwordEncoder=passwordEncoder;
	}
	
	public users LoginService(LoginDto dto) {
		String name = dto.getUsername();
		String pass = dto.getPassword();
		
		users user=userRepo.findByusername(name).orElseThrow(()-> new RuntimeException("Invaid username or password"));
       user.getUsername();
       
       if(!passwordEncoder.matches(pass, user.getPassword())) {
    	   throw new RuntimeException("Invalid username password");
       }
       
      return  user;
	}
	
	public String generateToken(users user) {
		String token;
		LocalDateTime currentTime = LocalDateTime.now();
		jwt_tokens existingToken = tokenRepo.findByuser_id(user.getUser_id());
		if(existingToken!=null && currentTime.isBefore(existingToken.getExpires_at())) {
			token = existingToken.getToken();
		} else {
		
		 JwtBuilder builder =  Jwts.builder();
		builder.setSubject(user.getUsername());
 builder.claim("role",user.getRole().name());
 builder.setIssuedAt(new Date());
 builder.setExpiration(new Date(System.currentTimeMillis()+360000));
 builder.signWith(key);
 token= builder.compact();
 System.out.println(token);
 if(existingToken != null) {
	 tokenRepo.delete(existingToken);
 }
 
 jwt_tokens jwtToken = new jwt_tokens(user, token,LocalDateTime.now().plusHours(1));
 tokenRepo.save(jwtToken);
		}
 return token;
	}
}
