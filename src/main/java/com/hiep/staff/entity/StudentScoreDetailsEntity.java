package com.hiep.staff.entity;

import lombok.Data;

@Data
public class StudentScoreDetailsEntity {
	private String classname;
	private String student_name;
    private Integer lesson;
    private Integer max_score;
    private Integer score;
    private Float score_percentage;
}