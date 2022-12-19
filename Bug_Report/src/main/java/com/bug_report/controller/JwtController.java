package com.bug_report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bug_report.dto.JwtResonse;
import com.bug_report.dto.JwtUserDto;
import com.bug_report.security.JwtUtil;
import com.bug_report.serviceImpl.CustomUserDetailService;

@RestController
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JwtUtil jwtUtil;
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtUserDto jwtUserDto) throws Exception
	{
		
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtUserDto.getUsername(), jwtUserDto.getPassword()));
			
		} catch (Exception e) {
			
			throw new Exception("Enter valid username or password");
		}
		
		UserDetails userDetails=this.customUserDetailService.loadUserByUsername(jwtUserDto.getUsername());
		String token = jwtUtil.generateToken(userDetails); 
		System.out.println(new JwtResonse(token));
		return ResponseEntity.ok(new JwtResonse(token));
	}
	
}
