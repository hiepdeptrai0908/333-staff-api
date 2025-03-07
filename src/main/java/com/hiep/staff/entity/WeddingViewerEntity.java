package com.hiep.staff.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WeddingViewerEntity {
    private Long id;
    private String user_name;
    private String relation;
    private LocalDateTime viewed_at;
}
