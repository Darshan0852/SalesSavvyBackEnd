package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.jwt_tokens;
@Repository
public interface TokenRepository  extends JpaRepository<jwt_tokens, Integer>{

}
