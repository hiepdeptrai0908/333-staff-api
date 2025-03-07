package com.hiep.staff.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WeddingViewerModel {
    private Long id;
    private String user_name;
    private String relation;
    private LocalDateTime viewed_at;
}
