package com.dpm.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.dpm.constant.SecurityConstants;
import com.dpm.service.UserDetailService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSercurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSercurityConfig.class);

	@Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) {
		
		try {
			auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
		} catch(UsernameNotFoundException e) {
			LOGGER.error("[Func] configure: user not found");
		} catch (Exception e) {
			LOGGER.error("[Func] configure: " + e.getMessage());
		}
		/*
		 * auth.inMemoryAuthentication().withUser("admin")
		 * .password("$2a$10$ufVKfsvtGnDITYuJHvOoauccEI6MhpSvg7RWufRg2vhkTkSXJo.Su")
		 * .roles(SecurityConstants.ROLEs.ADMIN) .and().withUser("user")
		 * .password("$2a$10$ufVKfsvtGnDITYuJHvOoauccEI6MhpSvg7RWufRg2vhkTkSXJo.Su")
		 * .roles(SecurityConstants.ROLEs.EMPLOYEE);
		 */
	}

	// Modify by LamPQT 1/19/2021 4:41PM
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		return new DefaultHttpFirewall();

	}

	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and()
		
				.headers().httpStrictTransportSecurity().disable()
		
				.and().authorizeRequests()
				// ThuanLV6 modify
				// LongVT7 enable sercurity
				.antMatchers(SecurityConstants.URLs.PERMIT_ALL).permitAll()
				.anyRequest().authenticated()

				.and().formLogin().loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/", true)
				.usernameParameter("username").passwordParameter("password")

				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")

				.and().csrf().disable()
				
				.exceptionHandling();

	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		// Modify by LamPQT 1/19/2021 4:41PM
		web.httpFirewall(allowUrlEncodedSlashHttpFirewall());

	}
}
