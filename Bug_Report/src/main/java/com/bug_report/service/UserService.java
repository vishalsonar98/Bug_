package com.bug_report.service;

import java.util.List;

import com.bug_report.dto.UserDto;


public interface UserService {
	public Object addUser(UserDto user);
	public List<UserDto> getAllUser();
	public Object getUserByEmpId(long empId);
	public Object updateByEmail(String email,UserDto userDto);
	public Object updateByEmpId(long empid, UserDto userDto);
	public String deleteByEmpId(long empid);
}
