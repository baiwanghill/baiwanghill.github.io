package com.bjsasc.orderservice.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;

public class MyPrincipalExtractor extends FixedPrincipalExtractor {

	@Override
	public Object extractPrincipal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Object obj = super.extractPrincipal(map);
		
		System.out.println(obj);
		return obj;
	}

}
