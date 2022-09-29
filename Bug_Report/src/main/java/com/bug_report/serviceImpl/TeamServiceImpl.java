package com.bug_report.serviceImpl;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bug_report.dto.TeamDto;
import com.bug_report.entity.TeamEntity;
import com.bug_report.repository.TeamRepository;
import com.bug_report.service.TeamService;
import com.bug_report.util.DateUtil;
import com.bug_report.util.MapObject;
import com.bug_report.util.ValidateData;

@Service
public class TeamServiceImpl implements TeamService {
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private ValidateData validateData;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private MapObject mapObject;

	@Override
	public String addTeam(TeamDto teamDto) {
		String messageString;
		String responseString = validateData.validateTeam(teamDto);
		if (!StringUtils.isBlank(responseString)) {
			messageString = responseString;
		} else {
			teamDto.setCreationDate(dateUtil.getCurrentDate());
			TeamEntity teamEntity = (TeamEntity) mapObject.mapObject(teamDto, TeamEntity.class);
			teamRepository.save(teamEntity);
			messageString = null;
		}
		return messageString;
	}

	@Override
	public List<TeamDto> getAllTeams() {
		List<TeamEntity> teamEntities = teamRepository.findAll();
		List<TeamDto> teamDtos = mapObject.teamEntityToTeamDto(teamEntities);
		return teamDtos;
	}

	@Override
	public String deleteTeamById(long id) {
		String response;
		String idString = "" + id;
		if (StringUtils.isBlank(idString)) {
			response = "Enter valid team id";
		} else {
			Object existingTeam = this.findTeamById(id);
			if (existingTeam instanceof String) {
				response = (String) existingTeam;
			} else {
				teamRepository.deleteById(id);
				response = null;
			}
		}
		return response;
	}

	@Override
	public Object findTeamById(long id) {
		Object response = null;
		TeamEntity teamEntity = null;
		String idString = "" + id;
		if (StringUtils.isBlank(idString)) {
			response = "Enter valid team id";
		} else {
			try {
				teamEntity = teamRepository.findById(id).get();
			} catch (Exception e) {
				response = "Record not found, Enter valid team id";
			}
			if (!ObjectUtils.isEmpty(teamEntity)) {
				TeamDto teamDto = (TeamDto) mapObject.mapObject(teamEntity, TeamDto.class);
				response = teamDto;
			}
		}
		return response;
	}

	@Override
	public String updateTeamById(long id, TeamDto teamDto) {
		String response = null;
		TeamEntity teamEntity = null;
		String idString = "" + id;
		if (StringUtils.isBlank(idString)) {
			response = "Enter valid team id";
		} else {
			String validateString = validateData.validateTeam(teamDto);
			if (!StringUtils.isBlank(validateString)) {
				response = validateString;
			} else {
				Object existingTeam = this.findTeamById(id);
				if (existingTeam instanceof String) {
					response = (String) existingTeam;
				} else {
					teamEntity = (TeamEntity) mapObject.mapObject(existingTeam, TeamEntity.class) ;
					teamEntity.setTeamName(teamDto.getTeamName());
					try {
						teamRepository.save(teamEntity);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					response=null;
				}
			}

		}
		return response;
	}

	
}
