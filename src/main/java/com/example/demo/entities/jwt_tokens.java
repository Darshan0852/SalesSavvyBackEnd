package com.example.demo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class jwt_tokens {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int token_id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
     users user;
	@Column
	String token;
	@Column
   LocalDateTime expires_at;	
	
	public jwt_tokens() {
		// TODO Auto-generated constructor stub
	}

	public jwt_tokens(int token_id, users user, String token, LocalDateTime expires_at) {
		super();
		this.token_id = token_id;
		this.user = user;
		this.token = token;
		this.expires_at = expires_at;
	}

	public jwt_tokens(users user, String token, LocalDateTime expires_at) {
		super();
		this.user = user;
		this.token = token;
		this.expires_at = expires_at;
	}

	public int getToken_id() {
		return token_id;
	}

	public void setToken_id(int token_id) {
		this.token_id = token_id;
	}

	public users getUser() {
		return user;
	}

	public void setUser(users user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpires_at() {
		return expires_at;
	}

	public void setExpires_at(LocalDateTime expires_at) {
		this.expires_at = expires_at;
	}
	
	
	
}
