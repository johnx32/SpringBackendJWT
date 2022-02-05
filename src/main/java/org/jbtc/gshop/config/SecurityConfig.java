package org.jbtc.gshop.config;

import org.jbtc.gshop.filter.JWTAuthenticationFilter;
import org.jbtc.gshop.filter.JWTAuthorizationFilter;
import org.jbtc.gshop.service.JWTServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JWTServiceContract jwtServiceContract;

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
		build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}*/
	@Bean
	public AuthenticationProvider authProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	/*@Override
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
	}*/


	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();

		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		source.registerCorsConfiguration("/**", config);*/




		http.authorizeRequests()
			//.antMatchers("/editar/**","/agregar/**","/eliminar")
			//.antMatchers("/editar/**","/agregar/**","/")
			//.hasRole("ADMIN")
			.antMatchers("/").permitAll()


			//.antMatchers("/","/api/categoria/**").permitAll()

            .anyRequest().authenticated()
			//.anyRequest().permitAll()
		.and()
			.addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtServiceContract))
				//.authorizeRequests()
				//.antMatchers(HttpMethod.OPTIONS, "/*").permitAll().and()
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
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().cors().configurationSource(corsConfigurationSource());
		//.antMatchers("/").permitAll();
		
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000",
                "http://192.223.119.81",
                "http://192.168.0.4"));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return source;
	}

	/*@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}*/

	/*
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	*/
	
	
}
