package com.flightadvisor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flightadvisor.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

	User findByUserNameAndPassword(String userName, String password);

}
