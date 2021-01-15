package com.crm.users;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CrmUser {

	@NotNull(message = "is Required")
	@Size(min = 1, message = "atleast size should be of length 1")
	private String name;

	@NotNull(message = "is Required")
	@Size(min = 4, message = "password length should be atleast 4")
	private String password;
	
	public CrmUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
