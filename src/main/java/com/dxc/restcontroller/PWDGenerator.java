package com.dxc.restcontroller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PWDGenerator {

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		System.out.println(passwordEncoder.encode("admin"));
	}

}
