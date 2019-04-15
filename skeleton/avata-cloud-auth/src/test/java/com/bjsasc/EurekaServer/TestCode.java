package com.bjsasc.EurekaServer;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestCode {
	@Test
	public void testMe() {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}
}
