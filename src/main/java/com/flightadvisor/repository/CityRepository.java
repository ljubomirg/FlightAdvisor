package com.flightadvisor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightadvisor.model.City;

public interface CityRepository extends JpaRepository<City, String>{

	City findByName(String name);

	List<City> findAllByName(String name);

}
