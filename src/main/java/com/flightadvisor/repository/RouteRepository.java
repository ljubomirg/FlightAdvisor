package com.flightadvisor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightadvisor.model.Route;
import com.flightadvisor.model.RouteAirport;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

	List<Route> findBySourceAirport(RouteAirport routeSourceAirport);

}
