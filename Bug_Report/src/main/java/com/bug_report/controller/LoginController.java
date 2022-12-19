package com.bug_report.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bug_report.dto.JwtUserDto;
import com.bug_report.security.JwtUtil;
import com.bug_report.serviceImpl.CustomUserDetailService;

@Controller
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/tokeng")
	public String generateToken(@ModelAttribute("user") JwtUserDto jwtUserDto, Model m) {
		String token = null;
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtUserDto.getUsername(), jwtUserDto.getPassword()));
			UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtUserDto.getUsername());
			token = jwtUtil.generateToken(userDetails);
			m.addAttribute("token", token);
			return "admin/dashboard";
			
		} catch (Exception e) {
			m.addAttribute("exception", "Enter valid username or password");
			return "admin/login";
		}

		
	}

	@RequestMapping("/login")
	public String login(Model m) {
		m.addAttribute("user", new JwtUserDto());
		return "admin/login";
	}
}
