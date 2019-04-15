package com.bjsasc.auth.controller;

import java.security.Principal;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	/**
	 * 资源服务器提供的受保护接口
	 * @param principal
	 * @return
	 */
    @RequestMapping("/user")
    public Principal user(Principal principal) {
        System.out.println(principal);
        OAuth2Authentication au = (OAuth2Authentication)principal;
        System.out.println(au.getPrincipal());
        return principal;
    }
    
}
