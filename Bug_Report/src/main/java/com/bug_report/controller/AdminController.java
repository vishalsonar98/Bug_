package com.bug_report.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bug_report.BugReportApplication;
import com.bug_report.dto.TeamDto;
import com.bug_report.dto.UserDto;
import com.bug_report.service.TeamService;
import com.bug_report.service.UserAndTeamService;
import com.bug_report.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController<T> {
	@Autowired
	private UserService userService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private UserAndTeamService userAndTeamService;
	
	
	private static final Logger log = LoggerFactory.getLogger(BugReportApplication.class);

	
	@GetMapping("/")
	public ResponseEntity<T> mapping()
	{
		return ResponseEntity.notFound().build();
	}
	
	/****************************User section*********************************/
	@SuppressWarnings("unchecked")
	@PostMapping("/users")
	public ResponseEntity<Object> addUser(@RequestBody UserDto user)
	{
		
		Object object=userService.addUser(user);
		if (ObjectUtils.anyNull(object)) {
			return new ResponseEntity<Object>("User created successfully",HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>((List<String>)object,HttpStatus.FORBIDDEN);
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
		Object responseObject = userService.getUserByEmpId(empId);
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
	
	/****************************Team section*********************************/
	@PostMapping("/teams")
	public ResponseEntity<Object> addTeam(@RequestBody TeamDto teamDto)
	{
		ResponseEntity<Object> responseEntity;
		String responseString=teamService.addTeam(teamDto);
		if (StringUtils.isBlank(responseString)) {
			responseEntity=new ResponseEntity<Object>(Collections.singletonMap("Message", "Team Created Successfully"),HttpStatus.CREATED);
		}
		else {
			responseEntity=new ResponseEntity<Object>(Collections.singletonMap("Message", responseString),HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	@GetMapping("/teams")
	public ResponseEntity<Object> getAllTeams()
	{
		ResponseEntity<Object> responseEntity;
		
		List<TeamDto> teamDtos=teamService.getAllTeams();
		if (ObjectUtils.isEmpty(teamDtos)) {
			responseEntity=new ResponseEntity<Object>(Collections.singletonMap("Message", "Record Not Found"),HttpStatus.NOT_FOUND);
		} else {
			responseEntity=new ResponseEntity<Object>(teamDtos,HttpStatus.FOUND);
		}
		return responseEntity;
	}
	
	@GetMapping("/teams/{id}")
	public ResponseEntity<Object> getTeamById(@PathVariable("id") long id)
	{
		ResponseEntity<Object> responseEntity;
		Object response = teamService.findTeamById(id);
		if (response instanceof String) {
			responseEntity = new ResponseEntity<Object>(Collections.singletonMap("Message", response),HttpStatus.BAD_REQUEST);
		} else {
			responseEntity = new ResponseEntity<Object>(response,HttpStatus.FOUND);
		}
		return responseEntity;
	}
	
	@DeleteMapping("/teams/{id}")
	public ResponseEntity<Object> deleteTeamById(@PathVariable long id)
	{
		ResponseEntity<Object> responseEntity;
		String response = teamService.deleteTeamById(id);
		if (StringUtils.isBlank(response)) {
			responseEntity = new ResponseEntity<Object>(Collections.singletonMap("Message", "Team Deleted Successfully"),HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Object>(Collections.singletonMap("Message", response),HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	@PutMapping("/teams/{id}")
	public ResponseEntity<Object> updateTeamById(@PathVariable("id") long id, @RequestBody TeamDto teamDto)
	{
		ResponseEntity<Object> responseEntity;
		String response = teamService.updateTeamById(id, teamDto);
		if (StringUtils.isBlank(response)) {
			responseEntity = new ResponseEntity<Object>(Collections.singletonMap("Message", "Team Updated Successfully"),HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Object>(Collections.singletonMap("Message", response),HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	/****************************add user to team*********************************/
	@PostMapping("/team/{tid}/user/{uid}")
	public ResponseEntity<Object> addMemberInTeam(@PathVariable("tid") long teamId,@PathVariable("uid") long userId)
	{
		ResponseEntity<Object> responseEntity;
		String responseString = userAndTeamService.addMemberToTeam(teamId, userId);
		if (StringUtils.isBlank(responseString)) {
			responseEntity = new ResponseEntity<Object>(Collections.singletonMap("Message", "Team member added Successfully"),HttpStatus.OK);
		}
		else {
			responseEntity = new ResponseEntity<Object>(Collections.singletonMap("Message", responseString),HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	@GetMapping("/getTeams/{empid}")
	public ResponseEntity<Object> getAllTeamsOfMember(@PathVariable("empid") long empid)
	{
		ResponseEntity<Object> responseEntity;
		Object response = userAndTeamService.getTeamOfUser(empid);
		if (response instanceof String) {
			responseEntity = new ResponseEntity<Object>(response,HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Object>(response,HttpStatus.OK);
		}
		
		 
		return responseEntity;
	}
	
	@PatchMapping("/team/{tid}/user/{uid}")
	public ResponseEntity<?> removeMemberFromTeam(@PathVariable("tid") long teamid, @PathVariable("uid") long userId)
	{
		ResponseEntity<?> responseEntity;
		String responseString = userAndTeamService.removeMemberFromTeam(teamid, userId);
		if (StringUtils.isEmpty(responseString)) {
			responseEntity = new ResponseEntity<Object>("Team data updated successfully...",HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Object>(responseString,HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	
	@GetMapping("/test")
	public String testingMeth()
	{
		return "testing page";
	}
	
	
	/*---------------------------------------------------------View apis--------------------------------------*/
	@RequestMapping("/login")
	public String login()
	{
		return "login";
	}
}
