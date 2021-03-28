package com.dpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dpm.service.CheckListSevice;
import com.dpm.service.EmployeeService;
import com.dpm.service.ProductLotSevice;

@SpringBootApplication
public class QamsApplication implements CommandLineRunner {
	
	//	<Update by LamPQT 09:30 AM>
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CheckListSevice checkListSevice;
	
	@Autowired
	private ProductLotSevice lotSevice;
	//	</Update by LamPQT 09:30 AM>
	
	public static void main(String[] args) {
		SpringApplication.run(QamsApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	} 
	
	
	//	<Update by LamPQT 09:30 AM>

	@Override
	public void run(String... args) throws Exception {
		// create entity each run app
	
		
	}	
	//	</Update by LamPQT 09:30 AM>

}
