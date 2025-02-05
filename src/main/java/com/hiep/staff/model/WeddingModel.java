package com.hiep.staff.model;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class WeddingModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String user_name;
	
	private String attendance;
	
	private String relation;
	
	private String wish;
	
	private LocalDateTime created_at;
}
