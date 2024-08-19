package com.hiep.staff.model;

import lombok.Data;

@Data
public class ScoreModel {
	private int score_id;
	private int class_id;
	private int student_id;
	private String classname;
	private String name;
	private int lesson;
	private int score;
	private int error;
	private String comment; 
}
