package com.bug_report.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto 
{
	private Long id;
	private String firstName;
	private String lastName;
	private String department;
	private String email;
	private String password;
	private Long roleId;
//	private UserRoleDto userTypeId;
}
