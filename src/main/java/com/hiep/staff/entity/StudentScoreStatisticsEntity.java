package com.hiep.staff.entity;

import lombok.Data;

@Data
public class StudentScoreStatisticsEntity {
	private Long student_id;
    private String student_name;
    private int class_id;
    private String classname;
    private Integer total_count_lesson;
    private Double average_score_percent;
}
