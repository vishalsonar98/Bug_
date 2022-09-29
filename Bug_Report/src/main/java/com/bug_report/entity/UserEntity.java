package com.bug_report.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "employee",uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "department")
	private String department;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	private UserRoleEntity userRoleId;
	
	@ManyToMany()
	@JoinTable(name = "team_assigned",joinColumns = {@JoinColumn(name="emp_id")},inverseJoinColumns = {@JoinColumn(name="team_id")})
	private Set<TeamEntity> team;
	
}
