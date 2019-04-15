package com.bjsasc.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private AuthenticationManager authenticationManager;

	@Qualifier("myUserDetailsService")
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().antMatchers("/login").antMatchers("/oauth/authorize", "/h2-console/**");

		http.authorizeRequests().antMatchers("/h2-console/**").permitAll() // 都可以访问
				.anyRequest().authenticated();
		http.formLogin().loginPage("/login").permitAll(); // 自定义登录页面，这里配置了 loginPage, 就会通过 LoginController 的 login
															// 接口加载登录页面

		http.csrf().disable();

		http.headers().frameOptions().sameOrigin();
		
		http.logout().permitAll().logoutSuccessHandler(
                (request, response, authentication) -> {
                    String callback = request.getParameter("callback");
                    if (callback == null){
                        callback = "/login?logout";
                    }
                    response.sendRedirect(callback);
                }
        );

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 配置用户名密码，这里采用内存方式，生产环境需要从数据库获取
		// auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123")).roles("USER");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
