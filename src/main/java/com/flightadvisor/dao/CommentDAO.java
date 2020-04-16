package com.flightadvisor.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightadvisor.model.Comment;

public interface CommentDAO extends JpaRepository<Comment, Integer> {

}
