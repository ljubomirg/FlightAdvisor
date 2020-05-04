package com.flightadvisor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightadvisor.model.City;
import com.flightadvisor.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	Page<Comment> findByCity(City city, Pageable paging);

}
