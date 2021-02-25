package com.ecommers.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommers.models.dto.UserCreateDto;
import com.ecommers.models.dto.UserDto;
import com.ecommers.models.dto.UserLoginDto;
import com.ecommers.models.dto.UserRecoveryDto;
import com.ecommers.models.dto.UserUpdateDTO;
import com.ecommers.security.JwtRequest;
import com.ecommers.security.JwtResponse;
import com.ecommers.service.Interface.IUserService;

@CrossOrigin
@RestController
public class UserController {

	private IUserService userService;

	private AuthenticationManager authenticationManager;
	
	public UserController(IUserService userService, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}
	
	@GetMapping(value = "/users")
	public ResponseEntity<List<UserDto>> getUsers() {
		List<UserDto> dto = this.userService.getAll();
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/{id}")
	public ResponseEntity <UserDto> getUser(@PathVariable Long id) {
		UserDto dto = this.userService.get(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PostMapping(value = "/users/create")
	public ResponseEntity<UserDto> CreateUser(@RequestBody UserCreateDto userData) {
		UserDto dto = this.userService.createNewUser(userData);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PutMapping(value = "/users/{id}")
	public ResponseEntity<UserDto> UpdateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userData) {
		UserDto dto = this.userService.updateUser(id, userData);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
		UserDto dto = this.userService.delete(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/recovery/send/{id}")
	public ResponseEntity <UserRecoveryDto> sendRecoveryPassword(@PathVariable Long id) {
		UserRecoveryDto dto = this.userService.sendRecoveryPassword(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/recovery/process/{key}")
	public ResponseEntity<Object> recoveryPassword(@PathVariable String key) {
		this.userService.recoveryPassword(key);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		UserLoginDto login = new UserLoginDto();
		login.setUserName(authenticationRequest.getUsername());
		login.setPassword(authenticationRequest.getPassword());
		String token = this.userService.login(login);
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
