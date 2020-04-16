package com.flightadvisor.dao;

import org.springframework.data.repository.CrudRepository;

import com.flightadvisor.model.User;

public interface UserDAO extends CrudRepository<User, String>{

	User findByUserNameAndPassword(String userName, String password);

}
