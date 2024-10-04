package com.hiep.staff.entity;

import lombok.Data;

@Data
public class StudentEntity {
	private int id;
	private int class_id;
	private String classname;
	private String name;
	private String sex;
	private String role;
	private String created_at;
}
