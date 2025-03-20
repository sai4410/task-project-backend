package com.teluguskillhub.taskproject.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.teluguskillhub.taskproject.entity.Users;
import com.teluguskillhub.taskproject.payload.UserDto;
import com.teluguskillhub.taskproject.repository.UserRepository;
import com.teluguskillhub.taskproject.service.UserService;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserDto createUser(UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users user = userDtoToEntity(userDto); //converted userDto to users class
		Users savedUser=  userRepository.save(user);
		return entityToUserDto(savedUser);
	}
	
	private Users userDtoToEntity(UserDto userDto) {
		Users users = new Users();
		users.setName(userDto.getName());
		users.setEmail(userDto.getEmail());
		users.setPassword(userDto.getPassword());
		return users;
	}
		
	private UserDto entityToUserDto(Users savedUser) {
		UserDto userDto = new UserDto();
		userDto.setId(savedUser.getId());
		userDto.setName(savedUser.getName());
		userDto.setEmail(savedUser.getEmail());
		userDto.setPassword(savedUser.getPassword());
		return userDto;
		
	}
	

}
