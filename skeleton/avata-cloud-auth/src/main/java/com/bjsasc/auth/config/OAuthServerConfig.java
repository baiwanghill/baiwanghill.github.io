package com.bjsasc.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@EnableAuthorizationServer
@Configuration
public class OAuthServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// TODO Auto-generated method stub
		super.configure(endpoints);
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("client") // clientId, 可以类比为用户名
				.secret(passwordEncoder.encode("secret")) // secret， 可以类比为密码
				.authorizedGrantTypes("authorization_code","password","implicit","client_credentials") // 授权类型，这里选择授权码
				.scopes("user_info") // 授权范围
				.autoApprove(true) // 自动认证
				.redirectUris("http://localhost:8862/login", "http://localhost:8863/login") // 认证成功重定向URL
				.accessTokenValiditySeconds(10000); // 超时时间，10s
//		 clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}

}
