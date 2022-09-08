package com.bug_report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bug_report.entity.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Long>{

}
