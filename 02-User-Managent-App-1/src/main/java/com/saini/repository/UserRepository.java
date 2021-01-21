package com.saini.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saini.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Serializable> {
	
	UserEntity findByEmail(String email);
	
	UserEntity findByEmailAndPassword(String email,String pwd);

}
