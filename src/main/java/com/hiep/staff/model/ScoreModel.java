package com.hiep.staff.model;

import lombok.Data;

@Data
public class ScoreModel {
	private int id;
	private int student_id;
	private String classname;
	private int lesson;
	private String name;
	private int score;
	private int error;
}
