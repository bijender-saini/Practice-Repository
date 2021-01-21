package com.saini.service;

import java.util.Map;

import com.saini.entity.UserEntity;
import com.saini.model.LoginForm;

public interface UserService {

	Map<Integer, String> findCountries();

	Map<Integer, String> findStates(Integer countryId);

	Map<Integer, String> findCities(Integer StateId);

	boolean saveUser(UserEntity user);

	boolean isUniqueEmail(String email);

	// Login-Functionality
	
	

	String checkLogin(String email,String password);

	boolean isTempPwdValid(String email, String tmpPwd);

	String unlockAccount(String email, String newPwd);

	String forgetPassword(String email);

	//Email Functionality
	
    boolean sendEmailUnlockAccount(String subject,String body ,String to);
    
    boolean sendEmailForgetPassward(String subject,String body ,String to);
	
	
}
