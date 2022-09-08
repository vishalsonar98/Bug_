package com.bug_report.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "userrole")
public class UserRoleEntity {
	@Id
	private Long id;
	
	@Column(name = "role")
	private String userType;

	@OneToMany(mappedBy = "userRoleId")
	private List<UserEntity> user;
}
