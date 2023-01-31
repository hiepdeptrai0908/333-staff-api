package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.SalarySetting;

@Mapper
public interface SalaryMapper {

	List<SalarySetting> getSetting();

	int updateSetting(SalarySetting salarySetting);
}
