package com.hiep.staff.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class SalaryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int salary_id;
	private String fullname;
	private int staff_id;
	private String salary_date;
	private String basic_salary;
	private String regular_time;
	private String up_salary;
	private int total_days;
	private String total_times;
	private String total_times_up;
	private int allowance;
	private int salary;
	private String create_at;
	private String create_user;

	private String delete_at;
	private String delete_user;

	private String update_at;
	private String update_user;

	private String status;
}
