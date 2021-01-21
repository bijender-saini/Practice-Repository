package com.saini.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.saini.service.UserService;

@RestController
public class ForgetPasswordController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/forget/{email}")
	String forgetPasswordSendingEmail(@PathVariable("email") String email)
	{
	    String msg=userService.forgetPassword(email);		
	  
	    return msg;
	}


}
