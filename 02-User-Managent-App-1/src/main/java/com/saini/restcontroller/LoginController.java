package com.saini.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saini.model.LoginForm;
import com.saini.service.UserService;
@RestController
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/login")
	String userLogin(@RequestBody LoginForm loginForm )
	{
		return userService.checkLogin(loginForm.getEmail(),loginForm.getPassword());

	}

}
