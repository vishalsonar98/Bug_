package com.bug_report.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "teams")
public class TeamEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "team_name")
	private String teamName;
	@Column(name = "creation_date")
	private String creationDate;
	
	@ManyToMany(mappedBy = "team")
	private List<UserEntity> team_member;
	
	
}
