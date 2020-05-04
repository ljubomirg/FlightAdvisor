package com.flightadvisor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightadvisor.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, String>{

	List<City> findAllByName(String name);

//	List<City> findByName(String name, Pageable topTen);

//	@Query(value="select * FROM CITY c join comment cm on c.name = cm.city_name where c.name = :name order by cm.created_date DESC limit :commentsLimit", nativeQuery = true)
//	@Query("FROM CITY c join comment cm on c.name = cm.city_name where c.name = :name order by cm.created_date DESC")
//	List<City> findAllByName(String name, Integer commentsLimit);

//	@Query("select c FROM City c join c.Comment cm where c.name = :name2 order by cm.createdDate DESC")
//	@Query("FROM City c join Comment cm on cm.city = c where c.name = :name2")
//	@Query("FROM Comment cm join cm.city c where c.name = :name2")
//	@Query("SELECT c FROM City c FETCH ALL PROPERTIES WHERE c.name = :name2")
//	@Query("select DISTINCT c FROM City c right join Comment cm on cm.city = c where c.name = :name2 group by cm.id")
//	Page <City> findAllByName(String name2, Pageable paging);
	
	

}

