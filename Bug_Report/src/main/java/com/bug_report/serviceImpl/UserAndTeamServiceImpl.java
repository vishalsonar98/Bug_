package com.bug_report.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bug_report.dto.TeamDto;
import com.bug_report.entity.TeamEntity;
import com.bug_report.entity.UserEntity;
import com.bug_report.repository.TeamRepository;
import com.bug_report.repository.UserRepository;
import com.bug_report.service.UserAndTeamService;
import com.bug_report.util.MapObject;

@Service
public class UserAndTeamServiceImpl implements UserAndTeamService {
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private MapObject mapObject;

	private static final Logger log = LoggerFactory.getLogger(UserAndTeamServiceImpl.class);

	@Override
	public String addMemberToTeam(long teamId, long userId) {
		String response = null;
		TeamEntity existingTeam = null;
		UserEntity existingUser = null;

		if (StringUtils.isAnyBlank(teamId + "", userId + "")) {
			response = "Enter valid Team Id or User Id";
		} else {
			try {
				existingTeam = teamRepository.findById(teamId).get();
				existingUser = userRepository.findById(userId).get();
				if (existingTeam.getTeamMember().contains(existingUser)) {
					log.error("member already present in team... team id = " + existingTeam.getId() + " member id = "
							+ existingUser.getId());
					response = "member already present in team...";

				} else {
					existingTeam.getTeamMember().add(existingUser);
					existingUser.getTeam().add(existingTeam);

					teamRepository.save(existingTeam);
					userRepository.save(existingUser);
				}

			} catch (Exception e) {
				response = "Team or User not found enter valid team Id or user id.";
				log.error(e.getMessage());

			}

		}
		return response;
	}

	@Override
	public String removeMemberFromTeam(long teamId, long userId) {
		String response = StringUtils.EMPTY;
		TeamEntity existingTeam = null;
		UserEntity existingUser = null;

		if (StringUtils.isAnyBlank(teamId + "", userId + "")) {
			response = "Enter valid Team Id or User Id";
		} else {
			try {
				existingTeam = teamRepository.findById(teamId).get();
				existingUser = userRepository.findById(userId).get();

				existingTeam.getTeamMember().remove(existingUser);
				existingUser.getTeam().remove(existingTeam);
				teamRepository.save(existingTeam);
				userRepository.save(existingUser);

			} catch (Exception e) {
				log.error(e.getMessage());
				response = "Team or Member not found enter valid Id";
			}
		}
		return response;
	}

	@Override
	public Object getTeamOfUser(long empId) {
		Object message = null;
		Object response;
		UserEntity user;
		List<TeamEntity> teamEntities;
		List<TeamDto> teamDto;
		try {
			response = userServiceImpl.getUserByEmpId(empId);
			if (response instanceof String) {
				message = response;
			} else {
				user = userRepository.findById(empId).get();
				teamEntities = teamRepository.findAllByTeamMember(user);
				teamDto = mapObject.teamEntityToTeamDto(teamEntities);
				message = teamDto;
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return message;
	}

}
