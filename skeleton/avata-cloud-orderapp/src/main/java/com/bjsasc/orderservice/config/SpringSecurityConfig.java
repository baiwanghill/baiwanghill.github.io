package com.bjsasc.orderservice.config;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 启用Spring自带的拦截器,security.basic.enabled=true则启用该配置
 * 
 * @author Administrator
 *
 */

@ConditionalOnProperty(name = "security.basic.enabled", havingValue = "true")
@Configuration
@EnableWebSecurity
@Slf4j
@Order(99)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("123").roles("admin", "user");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		log.error("used SpringSecurityConfig");
		http.antMatcher("/**").authorizeRequests().antMatchers("/login**").permitAll();
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/assets/**", "/layui/**").permitAll().anyRequest().authenticated();
		http.formLogin().loginPage("/login").permitAll().successHandler(loginSuccessHandler());
		http.logout().permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.logoutSuccessHandler(logoutSuccessHandler());
		http.sessionManagement().maximumSessions(10).expiredUrl("/login");

		http.headers().frameOptions().sameOrigin();

	}

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { // 登入处理
		return new SavedRequestAwareAuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				User userDetails = (User) authentication.getPrincipal();
				logger.info("USER : " + userDetails.getUsername() + " LOGIN SUCCESS !  ");
				super.onAuthenticationSuccess(request, response, authentication);
			}
		};
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() { // 登出处理
		return new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
					Authentication authentication) throws IOException, ServletException {
				try {
					User user = (User) authentication.getPrincipal();
					log.info("USER : " + user.getUsername() + " LOGOUT SUCCESS !  ");
				} catch (Exception e) {
					log.info("LOGOUT EXCEPTION , e : " + e.getMessage());
				}
				httpServletResponse.sendRedirect("/login");
			}
		};
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
