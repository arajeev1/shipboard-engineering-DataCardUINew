package com.example.securingweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/restricted").authenticated()
				.requestMatchers("/", "/home").permitAll()
				.requestMatchers("/", "/home", "/pmss").authenticated()
				.requestMatchers("/calendar").permitAll()
				.anyRequest().permitAll()
			)
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin((form) -> form
				.loginPage("/login")
				.successForwardUrl("/home")
				//.loginProcessingUrl()  // Need to try this if we need our own authentication
				//.failureForwardUrl("/login?error")  // This is not working
				.failureUrl("/login?error")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());
		System.out.println("Remove .csrf(AbstractHttpConfigurer::disable) in WebSecurityConfig when on Prod");
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		System.out.println("Temp sout Ajay-WebSecurityConfig:userDetailsService() - Need to load all types of users and their roles?Right now hardcoded");

		//create loadUserandRoles()

		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("admin@gmail.com")
				.password("admin")
				.roles("USER","MANAGE_USERS","MANAGE_ROLES","MANAGE_PERMISSIONS")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}
