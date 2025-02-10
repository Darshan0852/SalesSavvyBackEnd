package com.example.demo.filters;

import java.io.IOException;
import java.lang.System.Logger;
import java.util.Arrays;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Role;
import com.example.demo.entities.users;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.LoginAuthService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/api/*" , "/admin/*"})
@Component
public class AuthenticationFilter implements Filter {
	
	private LoginAuthService authService;
	private UserRepository userRepository;
	private static final String ALLOWED_ORIGIN = "http://localhost:5173";
//	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	private static final String [] Unauthorized_Paths = {
			"/api/user/register",
			"/api/auth/login"
	};
	
	
	public AuthenticationFilter(LoginAuthService authService,UserRepository userRepository) {
	
System.out.println("Filter Started!");
this.authService = authService;
this.userRepository=userRepository;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			executeFilterLogin(request, response, chain);
		}
		catch (Exception e) {
		
			e.getMessage();
		}
		
	}
	private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException { response.setStatus(statusCode); response.getWriter().write(message); }
	private void executeFilterLogin(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException{
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		
		String requestUri = httpRequest.getRequestURI();
		
		if(Arrays.asList(Unauthorized_Paths).contains(requestUri)) {
			chain.doFilter(request, response);
		}
		
		if(httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) 
		{
			setCORSHeaders(httpResponse);
			return;
		}
	
		
		String token = getAuthTokenFromCookies(httpRequest); 
		System.out.println(token); 
		if (token == null || !authService.ValidateToken(token)) 
		{ sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
		return; }
		
		String username = authService.extractUsername(token);
		Optional<users> userOptional = userRepository.findByusername(username); 
		if (userOptional.isEmpty()) { sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: User not found"); 
		return; }
		users authenticatedUser = userOptional.get(); 
		Role role = authenticatedUser.getRole(); logger.info("Authenticated User: {}, Role: {}", authenticatedUser.getUsername(), role);
		
		// Role-based access control
		if (requestUri.startsWith("/admin/") && role != Role.ADMIN) { sendErrorResponse(httpResponse, HttpServletResponse.SC_FORBIDDEN, "Forbidden: Admin access required"); return;
		
		}
		if (requestUri.startsWith("/api/") && (role != Role.CUSTOMER && role != Role.ADMIN)) { System.err.println("CHECKING..... "+ requestUri+" "+role); sendErrorResponse(httpResponse, HttpServletResponse.SC_FORBIDDEN, "Forbidden: Customer access required"); return; }
		
		httpRequest.setAttribute("authenticatedUser", authenticatedUser); chain.doFilter(request, response);
			}
	
	private void setCORSHeaders(HttpServletResponse response) { response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN); 
	response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); response.setHeader("Access-Control-Allow-Credentials", "true"); 
	response.setStatus(HttpServletResponse.SC_OK);}
	
	private String getAuthTokenFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies(); if (cookies != null)
		{
			return Arrays.stream(cookies) .filter(cookie -> "authToken".equals(cookie.getName())) .map(Cookie::getValue) .findFirst() .orElse(null); } return null; }

	
}
