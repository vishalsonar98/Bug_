package com.bug_report.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.bug_report.dto.UserDto;

public class ValidateData {
	
	public static List<String> validateUser(UserDto user)
	{
		String roleidString="";
		if (ObjectUtils.isNotEmpty(user.getRoleId())) {
			roleidString=""+user.getRoleId();
		}
		
		List<String> errorList=new ArrayList<>();
		Map<String, String> userMap=new HashMap<String, String>();
		userMap.put("firstName", user.getFirstName());
		userMap.put("lastName", user.getLastName());
		userMap.put("email", user.getEmail());
		userMap.put("department", user.getDepartment());
		userMap.put("password", user.getPassword());
		userMap.put("roleId",roleidString);
		
		if (StringUtils.isNoneBlank(user.getFirstName(),user.getLastName(),user.getDepartment(),user.getPassword(),user.getEmail(),roleidString)) {
			
			if (Pattern.matches("^([a-zA-Z]{2,20})$", user.getFirstName())) {
				if (Pattern.matches("^([a-zA-Z]{2,20})$", user.getLastName())) {
					if (Pattern.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
					        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", user.getEmail())) {
						if (user.getRoleId() == 101l || user.getRoleId()==102l || user.getRoleId()==255l) {
							
						} else {
							errorList.add("Enter valid User role id");
						}
						
					} else {
						errorList.add("Enter valid Email");
					}
				} else {
					errorList.add("Enter valid Last name");
				}
			}
			else {
				errorList.add("Enter valid First name");
			}
			
		}
		else {
			List<String> error=
			userMap.entrySet().stream().filter(e-> e.getValue().isBlank()).map(Map.Entry::getKey).collect(Collectors.toList());
			error.forEach(e-> errorList.add("Enter "+ e));
		}
		
		
		return errorList;
	}
	
	public static String validateObject(Object object)
	{
		if (Objects.isNull(object)) {
			return "Data not present...";
		}
		return null;
	}
}
