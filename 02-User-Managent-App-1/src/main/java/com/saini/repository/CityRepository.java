package com.saini.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saini.entity.CityEntity;
import com.saini.entity.StateEntity;

public interface CityRepository extends JpaRepository<CityEntity, Serializable> {

	List <CityEntity> findByStateId(Integer stateId);

}
