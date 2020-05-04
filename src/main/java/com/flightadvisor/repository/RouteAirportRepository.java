package com.flightadvisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightadvisor.model.RouteAirport;

@Repository
public interface RouteAirportRepository extends JpaRepository<RouteAirport, Integer> {

}
