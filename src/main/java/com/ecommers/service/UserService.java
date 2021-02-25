package com.ecommers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommers.exceptions.UserInvalidCredentialsException;
import com.ecommers.exceptions.UserIsBlockedException;
import com.ecommers.exceptions.UserNameAlreadyExistsException;
import com.ecommers.exceptions.UserNameInvalidException;
import com.ecommers.exceptions.UserNameRequiredException;
import com.ecommers.exceptions.UserNotPresentException;
import com.ecommers.exceptions.UserPasswordInvalidException;
import com.ecommers.exceptions.UserPasswordRequiredException;
import com.ecommers.exceptions.UserRecoveryInvalidException;
import com.ecommers.models.User;
import com.ecommers.models.dto.UserCreateDto;
import com.ecommers.models.dto.UserDto;
import com.ecommers.models.dto.UserLoginDto;
import com.ecommers.models.dto.UserRecoveryDto;
import com.ecommers.models.dto.UserUpdateDTO;
import com.ecommers.repository.IUserRepository;
import com.ecommers.security.JwtTokenUtil;
import com.ecommers.service.Interface.IUserService;

@Service
public class UserService  implements IUserService {
	@Autowired
	private IUserRepository UserRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public List<UserDto> getAll() {
		List<User> users = (List<User>) this.UserRepo.findAll();
		List<UserDto> dtos = new ArrayList<>(users.size());
		
		for (int i = 0; i < users.size(); i++) {
			if (!users.get(i).getIsActive()) {
				continue;
			}
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(users.get(i), userDto);
			userDto.setPassword(null);
			dtos.add(userDto);
		}		
		return dtos;
	}
	
	@Override
	public UserDto get(Long id) {
		UserDto userDto = new UserDto();
		Optional<User> user  = this.UserRepo.findById(id);
		if (!user.isPresent()) {
			throw new UserNotPresentException();
		}
		BeanUtils.copyProperties(user.get(), userDto);
		return userDto;
	}
	
	@Override
	public UserDto createNewUser(UserCreateDto user) {
		if (user.getUsername() == null) {
			throw new UserNameRequiredException();
		}
		if (user.getPassword() == null) {
			throw new UserPasswordRequiredException();
		}
		if (!this.isValidEmail(user.getUsername()) || user.getUsername().length() > 128) {
			throw new UserNameInvalidException();
		}
		if (!this.isValidPassword(user.getPassword())) {
			throw new UserPasswordInvalidException();
		}
		
		Optional<User> existingUser = this.UserRepo.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			throw new UserNameAlreadyExistsException(); 
		}
		
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setIsActive(true);
		newUser.setIsBlocked(false);
		
		com.ecommers.models.User savedUser = UserRepo.save(newUser);
		UserDto result = new UserDto();
		BeanUtils.copyProperties(savedUser, result);
		result.setId(savedUser.getId());
		return result;
	}
	
	@Override
	public UserDto updateUser(Long userId, UserUpdateDTO user) {
		if (user.getPassword() == null || user.getPassword() == "") {
			throw new UserPasswordRequiredException();
		}
		if (!this.isValidPassword(user.getPassword())) {
			throw new UserPasswordInvalidException();
		}
		Optional<User> existingUser = this.UserRepo.findById(userId);
		if (!existingUser.isPresent()) {
			throw new UserNotPresentException(); 
		}
		
		User _user = existingUser.get();
		_user.setPassword(bcryptEncoder.encode(user.getPassword()));
		this.UserRepo.save(_user);
		
		UserDto result = new UserDto();
		BeanUtils.copyProperties(_user, result);
		return result;
	}
	
	@Override
	public UserDto delete(Long userId) {
		Optional<User> existingUser = this.UserRepo.findById(userId);
		if (!existingUser.isPresent()) {
			throw new UserNotPresentException(); 
		}
		
		// Borrado fisico
		// this.UserRepo.delete(existingUser.get());
		
		// Borrado Logico.
		com.ecommers.models.User _user = existingUser.get();
		
		if (!_user.getIsActive()) {
			throw new UserNotPresentException(); 
		}
		_user.setIsActive(false);
		this.UserRepo.save(_user);
		
		UserDto result = new UserDto();
		BeanUtils.copyProperties(_user, result);
		return result;
	}
	
	@Override
	public UserRecoveryDto sendRecoveryPassword(Long userId) {
		Optional<User> existingUser = this.UserRepo.findById(userId);
		if (!existingUser.isPresent()) {
			throw new UserNotPresentException(); 
		}
		
		User user = existingUser.get();
		UUID uuid = UUID.randomUUID();
		String recoveryKey = uuid.toString();
		user.setRecoveryKey(recoveryKey);
		this.UserRepo.save(user);
		
		UserRecoveryDto result = new UserRecoveryDto();
		result.setEmail(user.getUsername());
		if (user.getIsBlocked()) {
			result.setSubject("Recuperación de usuario bloqueado");
		} else {
			result.setSubject("Recuperación de password");
		}
		
		result.setKey(recoveryKey);
		result.setMessage("Para recuperar el usuario confirme la solicitud haciendo click en el siguiente link: http://localhost:8080/users/recovery/" + uuid);
		
		return result;
	}
	
	@Override
	public void recoveryPassword(String key) {
		Optional<User> existingUser = this.UserRepo.findByRecoveryKey(key);
		if (!existingUser.isPresent()) {
			throw new UserRecoveryInvalidException();
		}
		
		User user = existingUser.get();
		user.setRecoveryKey(null);
		user.setIsBlocked(false);
		// user.setIs_active(true);
		this.UserRepo.save(user);
	}
	
	@Override
	public String login(UserLoginDto login) {
		if (login.getUserName() == null) {
			throw new UserNameRequiredException();
		}
		if (login.getPassword() == null) {
			throw new UserPasswordRequiredException();
		}
		
		Optional<User> existingUser = this.UserRepo.findByUsername(login.getUserName());
		if (!existingUser.isPresent()) {
			throw new UserNotPresentException(); 
		}
		if (existingUser.get().getIsBlocked()) {
			throw new UserIsBlockedException();
		}
		
		if (!bcryptEncoder.matches(login.getPassword(), existingUser.get().getPassword())) {
			User user = existingUser.get();
			Integer attempts = user.getLoginAttempts();
			if (attempts == null) {
				attempts = 0;
			}
			user.setLoginAttempts(attempts+1);
			
			if (user.getLoginAttempts() >= 3) {
				user.setIsBlocked(true);
			}
			
			this.UserRepo.save(user);
			throw new UserInvalidCredentialsException();
		}
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(existingUser.get().getUsername(), existingUser.get().getPassword(),
				new ArrayList<>());
		
		return jwtTokenUtil.generateToken(userDetails);
	}
	
	
	private Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private Pattern VALID_PASSWORD_REGEX = 
			Pattern.compile("^(?=.*?[A-Za-z_\\-.])(?=.*?[0-9]).{8,32}$", Pattern.CASE_INSENSITIVE);
	private Pattern INVALID_PASSWORD_CHAR_REGEX =
			Pattern.compile("[`~,<>;':\\\"\\/\\[\\]\\|{}()=+]", Pattern.CASE_INSENSITIVE);
	
	private boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
	}
	
	private boolean isValidPassword(String password) {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		Matcher matcherInvalidChars = INVALID_PASSWORD_CHAR_REGEX.matcher(password);
		
        return matcher.find() && !matcherInvalidChars.find();
	}

}
