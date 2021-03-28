package com.dpm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dpm.entity.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {

  Role findRoleByRole(String role);

}
