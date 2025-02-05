package com.example.demo.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.users;

public interface UserRepository extends JpaRepository<users, Integer>{

	Optional<users> findByusername(String username);
	Optional<users> findByemail(String email);
}
