package com.flightadvisor.repository;

import org.springframework.stereotype.Repository;

import com.flightadvisor.model.Airport;

@Repository
public interface AirportRepository extends BasicAirportRepository<Airport> {

	Airport findByCityName(String sourceCity);

}
