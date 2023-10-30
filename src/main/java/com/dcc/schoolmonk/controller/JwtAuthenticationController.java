package com.dcc.schoolmonk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dcc.schoolmonk.config.CustomAuthenticationProvider;
import com.dcc.schoolmonk.config.JwtTokenUtil;
import com.dcc.schoolmonk.service.JwtUserDetailsService;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.LoginDto;
import com.dcc.schoolmonk.vo.UserVo;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.POST, RequestMethod.GET })

@RestController
public class JwtAuthenticationController {

//	@Autowired
//	private CustomAuthenticationProvider authenticationManager;
	
	@Autowired
	CustomAuthenticationProvider authenticationProvider;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDto authenticationRequest) throws Exception {

		//authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		Authentication authentication = new UsernamePasswordAuthenticationToken(
										authenticationRequest.getUsername(), authenticationRequest.getPassword());
		authenticate(authentication);

		//final UserDetails userDetails = userDetailsService
		//		.loadUserByUsername(authenticationRequest.getUsername());
		
//		UserVo userVo = userService.findByUsername(authenticationRequest.getUsername());
		UserVo userVo = userService.findUserByEmail(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userVo);

		return ResponseEntity.ok(token);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserVo user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	//This won't work as we need a custom authentication manager
//	private void authenticate(String username, String password) throws Exception {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//		} catch (DisabledException e) {
//			throw new Exception("USER_DISABLED", e);
//		} catch (BadCredentialsException e) {
//			throw new Exception("INVALID_CREDENTIALS", e);
//		}
//	}
	
	private void authenticate(Authentication authentication) throws Exception {
		try {
			authenticationProvider.authenticate(authentication);
			//authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}