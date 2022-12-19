package com.bug_report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bug_report.entity.TeamEntity;
import com.bug_report.entity.UserEntity;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long>{
	List<TeamEntity> findAllByTeamMember(UserEntity userEntity);
}
