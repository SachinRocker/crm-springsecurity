package com.crm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource securityDataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//setting up the jdbc authentication
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/customer/showForm*").hasAnyRole("MANAGER","ADMIN")
		.antMatchers("/customer/save*").hasAnyRole("MANAGER","ADMIN")
		.antMatchers("/customer/delete").hasRole("ADMIN")
		.antMatchers("/customer/**").hasRole("EMPLOYEE")
		.antMatchers("/resources/**").permitAll()
		.and()
		.formLogin()
		.loginPage("/showMyLoginPage")
		.loginProcessingUrl("/authenticateTheUser")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");

	}
	
	@Bean
	public UserDetailsManager userDetailManager() {
		JdbcUserDetailsManager  userManager = new JdbcUserDetailsManager();
		userManager.setDataSource(securityDataSource);
		return userManager;
	}

	
	
	
	

}
