package com.flightadvisor.repository;

import org.springframework.data.repository.CrudRepository;

import com.flightadvisor.model.User;

public interface UserRepository extends CrudRepository<User, String>{

	User findByUserNameAndPassword(String userName, String password);

}
