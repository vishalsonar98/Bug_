package com.bug_report.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.bug_report.dto.TeamDto;
import com.bug_report.dto.UserDto;
import com.bug_report.entity.TeamEntity;
import com.bug_report.entity.UserEntity;
import com.bug_report.entity.UserRoleEntity;

@Component
public class MapObject {

	public  UserEntity userDtoToUserEntity(UserDto userDto) {
		UserEntity user = new UserEntity();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setDepartment(userDto.getDepartment());
		user.setPassword(userDto.getPassword());
		UserRoleEntity entity=new UserRoleEntity();
		entity.setId(userDto.getRoleId());
		user.setUserRoleId(entity);

		return user;
	}
	
	public static List<UserDto> userEntitysToUserDtos(List<UserEntity> userEntity) {
		List<UserDto> usersDtos = new ArrayList<>();
		ModelMapper modelMapper=new ModelMapper();
		userEntity.forEach(e->{
			usersDtos.add(modelMapper.map(e, UserDto.class));
		});
		return usersDtos;
	}
	
	public static UserDto userEntityToUserDto(UserEntity userEntity)
	{
		UserDto userDto= new UserDto();
		ModelMapper modelMapper=new ModelMapper();
		userDto=modelMapper.map(userEntity, UserDto.class);
		return userDto;
	}
	
	public  Object mapObject(Object object, Class<?> clazz) 
	{
		ModelMapper mapper=new ModelMapper();
		Object responseObject=mapper.map(object, clazz);
		return responseObject;
	}
	
	public List<TeamDto> teamEntityToTeamDto(List<TeamEntity> teamEntities) {
		List<TeamDto> teamDtos = new ArrayList<>();
		ModelMapper modelMapper=new ModelMapper();
		teamEntities.forEach(e->{
			teamDtos.add(modelMapper.map(e, TeamDto.class));
		});
		return teamDtos;
	}
}
