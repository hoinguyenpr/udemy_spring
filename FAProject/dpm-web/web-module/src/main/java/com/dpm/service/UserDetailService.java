package com.dpm.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dpm.entity.Employee;
import com.dpm.entity.Role;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private EmployeeService employeeService;

	// Get info user by username from database
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeService.getEmployeeByUsername(username);
		List<GrantedAuthority> authorities = getUserAuthority(employee.getRole());
		return buildUserForAuthentication(employee, authorities);
	}

	// Create list GrantedAuthority from roles
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		return grantedAuthorities;
	}

	// Create entity UserDetail from entity User
	private UserDetails buildUserForAuthentication(Employee e, List<GrantedAuthority> authories) {
		return new User(e.getUsername(), e.getPassword(), true, true, true, true, authories);
	}

}
