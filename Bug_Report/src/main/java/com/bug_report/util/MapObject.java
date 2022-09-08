package com.bug_report.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.bug_report.dto.UserDto;
import com.bug_report.entity.UserEntity;
import com.bug_report.entity.UserRoleEntity;

public class MapObject {

	public static UserEntity userDtoToUserEntity(UserDto userDto) {
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
	
	public static Object mapObject(Object object, Class<?> clazz) 
	{
		ModelMapper mapper=new ModelMapper();
		Object responseObject=mapper.map(object, clazz);
		return responseObject;
	}
}
