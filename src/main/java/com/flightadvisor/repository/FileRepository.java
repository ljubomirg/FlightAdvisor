package com.flightadvisor.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightadvisor.model.Airport;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<Airport, Integer> {

}
