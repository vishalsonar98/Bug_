package com.bug_report.serviceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bug_report.entity.TeamEntity;
import com.bug_report.entity.UserEntity;
import com.bug_report.repository.TeamRepository;
import com.bug_report.repository.UserRepository;
import com.bug_report.service.UserAndTeamService;

@Service
public class UserAndTeamServiceImpl implements UserAndTeamService {
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public String addMemberToTeam(long teamId, long userId) {
		String response = null;
		TeamEntity existingTeam=null;
		UserEntity existingUser=null;
		
		if (StringUtils.isAnyBlank(teamId+"",userId+"")) {
			response = "Enter valid Team Id or User Id";
		}
		else {
			try {
				existingTeam = teamRepository.findById(teamId).get();
				existingUser =  userRepository.findById(userId).get();
				existingTeam.getTeam_member().add(existingUser);
				existingUser.getTeam().add(existingTeam);
				
				teamRepository.save(existingTeam);
				userRepository.save(existingUser);
			} catch (Exception e) {
				response="Team or User not found enter valid team Id or user id.";
				
			}
								
		}
		return response;
	}

}
