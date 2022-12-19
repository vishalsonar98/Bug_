package com.bug_report.service;

public interface UserAndTeamService {
	public String addMemberToTeam(long teamId, long userId);
	public String removeMemberFromTeam(long teamId, long userId);
	public Object getTeamOfUser(long empId);
}
