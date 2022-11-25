package com.hiep.staff.entity;



import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class TimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int time_id;
	
	private int staff_id;
	private String fullname;

	private String time_in;
	private String time_out;
	
	private String date_in;
	private String date_out;
	
	private String break_in1;
	private String break_out1;
	private String break_time1;

	private String break_in2;
	private String break_out2;
	private String break_time2;
	
	private String break_total;
	private String work_time;

	private String update_time;
	private String update_date;
	private String update_user;

	private String status;

}

