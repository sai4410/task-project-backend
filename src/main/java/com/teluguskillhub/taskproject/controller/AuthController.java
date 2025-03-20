package com.teluguskillhub.taskproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teluguskillhub.taskproject.payload.JWTAuthResponse;
import com.teluguskillhub.taskproject.payload.LoginDto;
import com.teluguskillhub.taskproject.payload.UserDto;
import com.teluguskillhub.taskproject.security.JwtTokenProvider;
import com.teluguskillhub.taskproject.service.UserService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	//store the user data into DB using POST
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<JWTAuthResponse> loginUser(@RequestBody LoginDto loginDto) {
		Authentication authentication =  authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
				);
	//	System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JWTAuthResponse(token));
	}
	
}
