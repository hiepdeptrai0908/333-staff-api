package com.hiep.staff.entity;

import lombok.Data;

@Data
public class ClassEntity {
	private int id;
	private String classname;
	private int student_count;
	private int lesson_count;
	private String created_at;
}
