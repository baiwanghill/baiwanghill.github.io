package com.bjsasc.consumer.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

	private static final String AUTHORIZATION_HEADER = "Authorization";

	private static final String BEARER_TOKEN_TYPE = "Bearer";

	@Override
	public void apply(RequestTemplate template) {
		log.debug("Constructing Header {} for Token {}", AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE);
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication a = context.getAuthentication();
		if(a!=null&&a.getDetails() instanceof OAuth2AuthenticationDetails) {
			OAuth2AuthenticationDetails d = (OAuth2AuthenticationDetails)a.getDetails();
		template.header(AUTHORIZATION_HEADER,
				String.format("%s %s", BEARER_TOKEN_TYPE, d.getTokenValue()));
		}
	}
}
