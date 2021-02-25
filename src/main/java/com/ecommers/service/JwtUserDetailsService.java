package com.ecommers.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommers.repository.IUserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUserRepository UserRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<com.ecommers.models.User> user = this.UserRepo.findByUsername(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
				new ArrayList<>());
	}

	/*
	// TODO: Borrar.
	public com.ecommers.models.User save(UserDto user) {
		com.ecommers.models.User newUser = new com.ecommers.models.User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return UserRepo.save(newUser);
	}
*/
}