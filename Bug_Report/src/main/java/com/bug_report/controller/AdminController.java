package com.bug_report.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bug_report.dto.UserDto;
import com.bug_report.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController<T> {
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<T> mapping()
	{
		return ResponseEntity.notFound().build();
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/user")
	public ResponseEntity<Object> addUser(@RequestBody UserDto user)
	{
		
		Object object=userService.addUser(user);
		if (ObjectUtils.anyNull(object)) {
			return new ResponseEntity<Object>("User created successfully",HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>((List<String>)object,HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("/users")
	public ResponseEntity<Object> getAllUsers()
	{
		List<UserDto> response=userService.getAllUser();
		if (response.isEmpty()) {
			return new ResponseEntity<Object>("Record not found...",HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<Object>(response,HttpStatus.FOUND);
		}
	}
	
	@GetMapping("/user/{empid}")
	public ResponseEntity<Object> getEmployeeByEmpId(@PathVariable("empid") Long empId)
	{
		Object responseObject=userService.getUserByEmpId(empId);
		if (responseObject instanceof String) {
			return new ResponseEntity<Object>(responseObject,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(responseObject,HttpStatus.OK);
	}
	
	@PutMapping("/user/email/{email}")
	public ResponseEntity<Object> updateUserByEmail(@PathVariable("email") String email,@RequestBody UserDto userDto)
	{
		Object response=userService.updateByEmail(email, userDto);
		
		if (ObjectUtils.anyNull(response)) {
			return new ResponseEntity<Object>("User updated successfully",HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(response,HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/user/empid/{empid}")
	public ResponseEntity<Object> updateUserByEmpId(@PathVariable("empid") Long empid,@RequestBody UserDto userDto)
	{
		Object response=userService.updateByEmpId(empid, userDto);
		
		if (ObjectUtils.isEmpty(response)) {
			return new ResponseEntity<Object>(Collections.singletonMap("Message", "User updated successfully"),HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(Collections.singletonMap("Message", response),HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/user/empid/{empid}")
	public ResponseEntity<Object> deleteUserByEmpId(@PathVariable("empid") long empId)
	{
		String response=userService.deleteByEmpId(empId);
		
		if (StringUtils.isBlank(response)) {
			return new ResponseEntity<Object>(Collections.singletonMap("Message", "User Deleted Successfully..."),HttpStatus.OK);
		}
		else {
			
			return new ResponseEntity<Object>(Collections.singletonMap("Message", response),HttpStatus.NOT_FOUND);
		}
	}
}
