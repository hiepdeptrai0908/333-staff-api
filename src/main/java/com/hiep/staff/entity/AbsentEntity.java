package com.hiep.staff.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AbsentEntity {

    private Long id;
    
    private Long log_id;

    private Integer class_id;
    
    private String classname;

    private Integer student_id;
    
    private String student_name;

    private Boolean absent;

    private String reason;

    private String part;

    private LocalDate absent_at;

}
