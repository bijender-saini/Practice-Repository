package com.saini.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@Entity
@Table(name = "USER_MASTER")
public class UserEntity {

	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Integer userId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastname;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHNO")
	private long phNo;

	@Column(name = "DOB")
	private String DOB;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "STATE")
	private String state_Id;
	
	@Column(name = "COUNTRY")
	private Integer country_Id;
	
	@Column(name = "CITY")
	private Integer city_Id;

	@Column(name = "ACCOUNT_STATUS")
	private String accStatus;

}
