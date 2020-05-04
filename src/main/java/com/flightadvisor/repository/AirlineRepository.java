package com.flightadvisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightadvisor.model.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

}
