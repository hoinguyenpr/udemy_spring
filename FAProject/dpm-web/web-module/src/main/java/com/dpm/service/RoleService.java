package com.dpm.service;

import java.util.Optional;

import com.dpm.entity.Role;

public interface RoleService {

	Optional<Role> findById(Integer id);
  
  Role findByRole(String role);

}
