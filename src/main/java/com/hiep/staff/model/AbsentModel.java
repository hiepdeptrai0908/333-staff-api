package com.hiep.staff.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AbsentModel {
	
    private Integer class_id;

    private Integer student_id;
    
    private Long log_id;

    private Boolean absent;

    private String reason;

    private String part;

    private LocalDate absent_at;
}
