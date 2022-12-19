package com.bug_report.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bug_report.dto.CustomUserDetails;
import com.bug_report.entity.UserEntity;
import com.bug_report.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("User Not Found!!");
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
//		new User(user.getEmail(),user.getPassword(),new ArrayList<>())
		return customUserDetails;
	}

}
