package com.saini.model;

import lombok.Data;

@Data
public class UnlockAccount {
	
	private String email;
	private String newPwd;
	private String tmpPwd;
	private String confirmPwd;
	

}
