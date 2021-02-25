package com.ecommers.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommers.models.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findByRecoveryKey(String recoveryKey);
	
	Optional<User> findByUsernameAndPassword(String username, String password);
}

