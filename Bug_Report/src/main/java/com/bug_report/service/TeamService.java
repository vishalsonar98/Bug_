package com.bug_report.service;

import java.util.List;

import com.bug_report.dto.TeamDto;

public interface TeamService {
	public String addTeam(TeamDto teamDto);
	public List<TeamDto> getAllTeams();
	public String deleteTeamById(long id);
	public Object findTeamById(long id);
	public String updateTeamById(long id,TeamDto teamDto);
	
}
