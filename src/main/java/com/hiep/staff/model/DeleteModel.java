package com.hiep.staff.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class DeleteModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;

	private String delete_at;
	private String delete_user;

	private String status;
}
