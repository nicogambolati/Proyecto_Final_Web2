package com.ecommers.service.Interface;

import java.util.List;

import com.ecommers.models.dto.UserCreateDto;
import com.ecommers.models.dto.UserDto;
import com.ecommers.models.dto.UserLoginDto;
import com.ecommers.models.dto.UserRecoveryDto;
import com.ecommers.models.dto.UserUpdateDTO;

public interface IUserService {
	
	List <UserDto> getAll ();
	
	UserDto get(Long id);

	UserDto createNewUser(UserCreateDto user);

	UserDto updateUser(Long userId, UserUpdateDTO user);

	UserDto delete(Long userId);

	void recoveryPassword(String key);

	String login(UserLoginDto login);

	UserRecoveryDto sendRecoveryPassword(Long userId);
}
