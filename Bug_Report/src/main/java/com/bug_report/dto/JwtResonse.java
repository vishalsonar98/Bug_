package com.bug_report.dto;

import lombok.Data;

@Data
public class JwtResonse {
	
	String token;
	public JwtResonse(String token) {
		super();
		this.token = token;
	}
}
