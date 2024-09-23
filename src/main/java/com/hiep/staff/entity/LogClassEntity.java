package com.hiep.staff.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LogClassEntity {

    private Long id;

    private Integer class_id;

    private Integer class_size;

    private String teacher;

    private Integer count_absent;
    
    private String content;

    private String part;

    private LocalDate log_at;

}
