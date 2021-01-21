package com.saini.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saini.entity.StateEntity;

public interface StateRepository extends JpaRepository<StateEntity, Serializable> {
	
	List <StateEntity> findByCountryId(Integer CountryId);

}
