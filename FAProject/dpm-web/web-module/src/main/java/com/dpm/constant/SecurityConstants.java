package com.dpm.constant;

public interface SecurityConstants {

	interface URLs {
		String[] PERMIT_ALL = { "/login", "/css/**", "/js/**", "/images/**", "/vendors/**", "/fonts/**", "/database/**" };
	}

	interface ROLEs {
		String ADMIN = "ADMIN";
		String EMPLOYEE = "EMPLOYEE";
	}
	
	interface HasRoles {
		String ADMIN = "hasRole('ADMIN')";
		String EMPLOYEE = "hasRole('EMPLOYEE')";
		String ALL = "hasRole('ADMIN', 'EMPLOYEE')";
	}

}
