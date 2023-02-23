package com.hiep.staff.model;

import lombok.Data;

@Data
public class SearchSalaryModel {

	private int staff_id;
	private String month;

	private String year;
	private String yearMonth;
}
