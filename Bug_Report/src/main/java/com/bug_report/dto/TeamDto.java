package com.bug_report.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {
	private long id;
	private String teamName;
	private String creationDate;
}
