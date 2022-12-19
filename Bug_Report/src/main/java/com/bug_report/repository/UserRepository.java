package com.bug_report.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.security.core.userdetails.User;

import com.bug_report.entity.TeamEntity;
import com.bug_report.entity.UserEntity;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	public UserEntity findByEmail(String email);
	@Query("select u from UserEntity u where u.email = :email")
	public User getUserByUserName(@Param("email") String email);
	
	public Set<UserEntity> findAllByTeam(TeamEntity teamEntity);
	
}
