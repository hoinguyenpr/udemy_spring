package com.dpm.service.impl;

import com.dpm.dao.RoleDAO;
import com.dpm.entity.Role;
import com.dpm.service.RoleService;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

	private RoleDAO roleDAO;

	public RoleServiceImpl(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	public Optional<Role> findById(Integer id) {
		return roleDAO.findById(id);
	}

	@Override
	public Role findByRole(String role) {
		// TODO Auto-generated method stub
		return roleDAO.findRoleByRole(role);
	}

}
