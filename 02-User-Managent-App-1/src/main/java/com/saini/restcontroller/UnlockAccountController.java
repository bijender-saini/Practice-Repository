package com.saini.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saini.model.UnlockAccount;
import com.saini.service.UserService;

@RestController
public class UnlockAccountController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping(value="/unlockAccount" )
	String   UnlockUserAcc(@RequestBody UnlockAccount acc)
	{
     String msg=null;
	  	
	boolean isValid = userService.isTempPwdValid( acc.getEmail(),acc.getTmpPwd());
	if(isValid) {
		msg = userService.unlockAccount(acc.getEmail(), acc.getNewPwd());
		
		return msg ;
	}
	
	return msg;
	}
}
