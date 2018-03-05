package com.dxc.restcontroller.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.restcontroller.model.ResponseModel;

@RestController
@RequestMapping("/api/v1")
public class RESTController {

	@GetMapping("/helloworld")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String helloWorld() {
		return new ResponseModel("success", "Hello world!").toJSON();
	}
}
