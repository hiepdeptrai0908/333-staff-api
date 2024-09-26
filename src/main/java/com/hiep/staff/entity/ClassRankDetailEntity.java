package com.hiep.staff.entity;

import lombok.Data;

@Data
public class ClassRankDetailEntity {
	private int class_id;                   // class_id
    private String classname;                // classname
    private int total_absent;                // total_absent (tổng số buổi vắng)
    private double average_score_percent;     // average_score_percent
    private int total_lessons;               // total_lessons
    private int total_log_class;              // total_log_class
}
