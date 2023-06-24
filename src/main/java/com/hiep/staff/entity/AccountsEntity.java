package com.hiep.staff.entity;



import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class AccountsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;

	private int staff_id;

	private String username;

	private String password;

	private String fullname;
	
	private String sex;
	
	private String birthday;
	
	private String email;

	private String phone_number;
	
	private String address;
	
	private int money_support;
	
	private int basic_salary;

	private String create_at;
	private String create_user;

	private String delete_at;
	private String delete_user;

	private String update_at;
	private String update_user;

	private String status;
}

