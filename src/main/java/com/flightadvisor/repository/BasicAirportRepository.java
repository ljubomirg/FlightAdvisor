package com.flightadvisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightadvisor.model.BasicAirport;

@Repository
public interface BasicAirportRepository<T extends BasicAirport> extends JpaRepository<T, Integer> {

}
