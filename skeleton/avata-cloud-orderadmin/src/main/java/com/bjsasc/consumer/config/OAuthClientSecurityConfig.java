package com.bjsasc.consumer.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;

@ConditionalOnProperty(name = "security.basic.enabled", havingValue = "false")
@Configuration
@Order(1210)
public class OAuthClientSecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${security.oauth2.client.access-token-uri}")
	private String oauthHost;
	
	@Bean
	public PrincipalExtractor myPrincipalExtractor() {
		return new MyPrincipalExtractor();
	}
	/**
	 *  添加Header相关信息
	 * @return
	 * @Bean
	 */
	public RequestInterceptor headerInterceptor() {
	    return template -> {
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	        Enumeration<String> headerNames = request.getHeaderNames();
	        if (headerNames != null) {
	            while (headerNames.hasMoreElements()) {
	                String name = headerNames.nextElement();
	                String values = request.getHeader(name);
	                template.header(name, values);
	            }
	        }
	    };
	}
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().antMatcher("/**").authorizeRequests().antMatchers("/", "/login**").permitAll()
				.anyRequest().authenticated();
		http.logout().permitAll().logoutSuccessHandler(((request, response, authentication) -> {
			response.sendRedirect(oauthHost.split("oauth")[0] + "logout?callback=http://" + request.getHeader("Host"));
		}));
	}

}
