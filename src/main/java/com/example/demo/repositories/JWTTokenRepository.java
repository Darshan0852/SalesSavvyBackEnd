package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.JWTToken;
@Repository
public interface JWTTokenRepository  extends JpaRepository<JWTToken, Integer>{

	  Optional<JWTToken> findByToken(String token);

	@Query("select t from JWTToken t where t.user.userId = :userId")
	JWTToken findByUserId(@Param("userId") int userId);


	@Modifying
	@Transactional
	@Query("delete from JWTToken t where t.user.userId = :userId")
	void deleteByUserId(@Param("userId") int userId);

}
