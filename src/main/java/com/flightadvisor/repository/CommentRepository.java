package com.flightadvisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightadvisor.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
