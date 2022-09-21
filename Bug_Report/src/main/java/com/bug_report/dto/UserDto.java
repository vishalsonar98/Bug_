 package com.bug_report.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto 
{
//	@JsonProperty("id")
	private Long id;
//	@JsonProperty("first_name")
	private String firstName;
//	@JsonProperty("Last_name")
	private String lastName;
//	@JsonProperty("department")
	private String department;
//	@JsonProperty("email")
	private String email;
//	@JsonProperty("password")
	private String password;
//	@JsonProperty("role_id")
	private Long roleId;
//	private UserRoleDto userTypeId;
}
