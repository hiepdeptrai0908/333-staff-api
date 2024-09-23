package com.hiep.staff.entity;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class ClassLogDetails {
	private int class_id;
	private int log_id;
	private String part;
    private Date log_at;
    private String classname;
    private int student_count;
    private String teacher;
    private String content;
    private int count_absent;
    private int absent_true_count;
    private int absent_false_count;
    private List<AbsentEntity> absentees;
}
