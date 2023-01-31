package com.hiep.staff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiep.staff.entity.SalarySetting;
import com.hiep.staff.mapper.AccountsMapper;
import com.hiep.staff.mapper.SalaryMapper;
import com.hiep.staff.mapper.TimeMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/salary")
@CrossOrigin
public class SalaryController {

	@Autowired
	AccountsMapper accountsMapper;
	
	@Autowired
	SalaryMapper salaryMapper;

	@Autowired
	TimeMapper timeMapper;
	
	@GetMapping("get-salary-setting")
	public List<SalarySetting> getSalarySettings() {
		List<SalarySetting>  datas = salaryMapper.getSalarySetting();
		log.info("datas: {}",datas);
		return datas;
	}

}
