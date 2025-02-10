package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.jwt_tokens;
@Repository
public interface TokenRepository  extends JpaRepository<jwt_tokens, Integer>{

	
	@Query("select t from jwt_tokens t where t.user.user_id = :user_id")
	jwt_tokens findByuser_id(@Param("user_id") int user_id);
	
	
	@Modifying
	@Transactional
	@Query("delete from jwt_tokens t where t.user.user_id = :user_id")
	void deleteByuser_id(@Param("user_id") int user_id);
	
	Optional<jwt_tokens> findBytoken(String token);
}
