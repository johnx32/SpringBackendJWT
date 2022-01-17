package org.jbtc.gshop.config;

import org.aspectj.weaver.ast.And;
import org.jbtc.gshop.filter.JWTAuthenticationFilter;
import org.jbtc.gshop.filter.JWTAuthorizationFilter;
import org.jbtc.gshop.service.JWTServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userdetails;
	@Autowired
	private JWTServiceContract jwtServiceContract;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
			.withUser("admin")
			.password("{noop}admin")
			.roles("ADMIN","USER")
		.and()
			.withUser("user2")
			.password("{noop}123")
			//.password("123")
			.roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//.antMatchers("/editar/**","/agregar/**","/eliminar")
			//.antMatchers("/editar/**","/agregar/**","/")
			//.hasRole("ADMIN")
			.antMatchers("/").permitAll()
			//.antMatchers("/","/api/categoria/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtServiceContract))
			.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtServiceContract))
	        /*.formLogin()
	        	//.successHandler(null)
	        	.loginPage("/login")
	        .and()
	        .logout().permitAll()
	        .and()
	        .exceptionHandling().accessDeniedPage("/errores/403")
		.and()*/
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//.antMatchers("/").permitAll();
		
	}

	/*
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	*/
	
	
}
