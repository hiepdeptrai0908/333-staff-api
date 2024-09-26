package com.hiep.staff.entity;

import lombok.Data;

@Data
public class StudentAbsenceStatisticsEntity {
	private Integer student_id;
    private String student_name;
    private Integer class_id;
    private String classname;
    private Integer total_absent;
    private Integer absent_true;
    private Integer absent_false;

}
