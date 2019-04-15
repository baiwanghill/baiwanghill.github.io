package com.bjsasc.consumer.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	/**
	 * 自定义登录页面
	 * @return
	 */
    @GetMapping("/login")
    public String login() {
    	return "login";
    }
    @GetMapping("/user")
    @ResponseBody
//    @Secured("ROLE_USER")
    public Authentication getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
}
