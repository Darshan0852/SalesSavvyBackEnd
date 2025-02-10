package com.example.demo.services;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Value;

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
import java.security.Key;


@Service
public class LoginAuthService {

	UserRepository userRepo;
	TokenRepository tokenRepo;
	private PasswordEncoder passwordEncoder;
	private final Key SIGNING_KEY;
	
	public LoginAuthService(TokenRepository tokenRepo,UserRepository userRepo,PasswordEncoder passwordEncoder, @Value("${jwt.secret}")String jwtSecret) {
		// TODO Auto-generated constructor stub
		this.tokenRepo = tokenRepo;
		this.userRepo=userRepo;
		this.passwordEncoder=passwordEncoder;
		this.SIGNING_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}
	
	public users LoginService(LoginDto dto) {
		String name = dto.getUsername();
		String pass = dto.getPassword();
		
		users user=userRepo.findByusername(name).orElseThrow(()-> new RuntimeException("Invaid username or password"));
       user.getUsername();
       
       if(!passwordEncoder.matches(pass, user.getPassword())) {
    	   throw new RuntimeException("Invalid password");
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
 builder.setExpiration(new Date(System.currentTimeMillis()+3600 * 1000));
 builder.signWith(SIGNING_KEY , SignatureAlgorithm.HS512);
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
	
	public boolean ValidateToken(String Token) {
		try {
			System.err.print("Validating Token!");
			 if (Token.startsWith("Bearer ")) {
		            Token = Token.substring(7);
		        }
			
			Jwts.parserBuilder()
			.setSigningKey(SIGNING_KEY)
			.build().
			parseClaimsJws(Token);
			Optional<jwt_tokens> jwtTokens = tokenRepo.findBytoken(Token);
			if(jwtTokens.isPresent()) {
				System.err.println("Token Expiry"+jwtTokens.get().getExpires_at());
				return jwtTokens.get().getExpires_at().isAfter(LocalDateTime.now());
			}
			
			return false;
		}catch (Exception e) {
			// TODO: handle exceptio
		System.err.print("Token validation failed"+e.getMessage());
			return false;
		}
	}
	
	public String extractUsername(String token)
{
		return Jwts.parserBuilder().setSigningKey(SIGNING_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
				}
}
