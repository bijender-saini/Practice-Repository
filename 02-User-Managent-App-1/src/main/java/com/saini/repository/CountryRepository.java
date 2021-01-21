package com.saini.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saini.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Serializable> {
	
	

}
