package com.hiep.staff.entity;

import lombok.Data;

@Data
public class ResultStudentEntity {
	private int id;
	private int class_id;
	private int student_id;
	private int lesson;
	private int max_score;
	private String name;
	private int score;
	private int error;
	private String comment;
	private String created_at;
	
}
