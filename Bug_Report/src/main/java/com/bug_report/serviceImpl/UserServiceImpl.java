package com.bug_report.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bug_report.dto.UserDto;
import com.bug_report.entity.TeamEntity;
import com.bug_report.entity.UserEntity;
import com.bug_report.repository.TeamRepository;
import com.bug_report.repository.UserRepository;
import com.bug_report.service.UserService;
import com.bug_report.util.MapObject;
import com.bug_report.util.ValidateData;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
 	private UserRepository userRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private MapObject mapObject;
	
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public Object addUser(UserDto user) {
		UserEntity userEntity = null;
		Object responseObject;
		if (!ObjectUtils.isEmpty(user)) {

			List<String> errors = ValidateData.validateUser(user);
			if (errors.isEmpty()) {
				userEntity = mapObject.userDtoToUserEntity(user);
				UserEntity existingUser;
				existingUser = userRepository.findByEmail(userEntity.getEmail());
				if (!ObjectUtils.isEmpty(existingUser)) {
					return Arrays.asList("email already exist");
				}
				userRepository.save(userEntity);
				responseObject = null;
			} else {
				responseObject = errors;
			}
		} else {
			responseObject = "Enter details of user properly...";
		}
		return responseObject;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<UserEntity> userEntities = userRepository.findAll();
		List<UserDto> users = MapObject.userEntitysToUserDtos(userEntities);
		return users;
	}

	@Override
	public Object getUserByEmpId(long empId) {
		Object responseObject;
		UserEntity user = null;
		if (StringUtils.isEmpty("" + empId)) {
			responseObject = "Enter valid employee id";
		}
		else {
			try {
				user = userRepository.findById(empId).get();
				responseObject = MapObject.userEntityToUserDto(user);
			} catch (Exception e) {
				log.error(e.getMessage());
				responseObject = "Record not found..";

			}
			
		}
		
		return responseObject;
	}

	@Override
	public Object updateByEmail(String email, UserDto userDto) {
		Object responseObject;
		if (StringUtils.isBlank(email)) {
			responseObject = "Enter valid email...";
		}
		UserEntity existringUser = userRepository.findByEmail(email);
		if (ObjectUtils.isEmpty(existringUser)) {
			responseObject = "User not found enter valid email...";
		}
		List<String> responseList = ValidateData.validateUser(userDto);
		if (responseList.isEmpty()) {
			UserEntity userEntity = (UserEntity) mapObject.mapObject(userDto, UserEntity.class);
			existringUser.setFirstName(userEntity.getFirstName());
			existringUser.setLastName(userEntity.getLastName());
			existringUser.setUserRoleId(userEntity.getUserRoleId());
			existringUser.setEmail(userEntity.getEmail());
			existringUser.setPassword(userEntity.getPassword());
			existringUser.setDepartment(userEntity.getDepartment());
			userRepository.save(existringUser);
			responseObject = null;
		} else {
			responseObject = responseList;
		}
		return responseObject;
	}

	@Override
	public Object updateByEmpId(long empid, UserDto userDto) {
		String messageString = StringUtils.EMPTY;
		UserEntity existringUser;
		List<String> responseList;
		Object response;
		UserEntity userEntity;

		response = this.getUserByEmpId(empid);
		if (response instanceof String) {
			messageString = "User not found enter valid empid...";
		} else {

			existringUser = userRepository.findById(empid).get();
			responseList = ValidateData.validateUser(userDto);
			if (responseList.isEmpty()) {
				userEntity = (UserEntity) mapObject.mapObject(userDto, UserEntity.class);
				existringUser.setFirstName(userEntity.getFirstName());
				existringUser.setLastName(userEntity.getLastName());
				existringUser.setUserRoleId(userEntity.getUserRoleId());
				existringUser.setEmail(userEntity.getEmail());
				existringUser.setPassword(userEntity.getPassword());
				existringUser.setDepartment(userEntity.getDepartment());
				userRepository.save(existringUser);

			} else {
				return responseList;
			}

		}

		return messageString;
	}

	@Override
	public String deleteByEmpId(long empid) {
		String messageString = null;
		Object response;

		try {
			response = this.getUserByEmpId(empid);
			if (response instanceof String) {
				messageString = (String) response;
			} else {
				UserEntity user = userRepository.findById(empid).get();
				List<TeamEntity> teams = teamRepository.findAllByTeamMember(user);
				teams.forEach(e->{
					e.getTeamMember().remove(user);
					user.getTeam().remove(e);
					teamRepository.save(e);
					userRepository.save(user);
				});
				userRepository.deleteById(empid);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return messageString;
	}

}
