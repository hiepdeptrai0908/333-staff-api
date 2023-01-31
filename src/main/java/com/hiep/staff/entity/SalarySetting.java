package com.hiep.staff.entity;



import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class SalarySetting {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int salary_id;

	private int basic_salary;
	
	private int up_salary;
	
}

