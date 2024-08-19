package com.hiep.staff.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ScoreEntity {
	private int score_id;
	private int student_id;
	private String name; 
	private int lesson;
	private int score;
	private int error;
	private String comment; 
	private LocalDateTime created_at;
	
}
