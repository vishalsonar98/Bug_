package com.bug_report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bug_report.entity.TeamEntity;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long>{

}
