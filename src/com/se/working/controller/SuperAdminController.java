package com.se.working.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuperAdminController {
	@RequestMapping("/aa")
	public String initDatabaseI() {
		System.out.println("aa");
		return null;
	}
}
