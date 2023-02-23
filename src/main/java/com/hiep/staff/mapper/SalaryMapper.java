package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.AllowanceEntity;
import com.hiep.staff.entity.SalaryEntity;
import com.hiep.staff.entity.SalarySetting;
import com.hiep.staff.entity.TimeEntity;
import com.hiep.staff.model.SearchSalaryModel;

@Mapper
public interface SalaryMapper {

	List<SalarySetting> getSetting();

	int updateSetting(SalarySetting salarySetting);

	List<SalaryEntity> getSalaryUser(SearchSalaryModel searchSalaryModel);

	int checkHasInserted(SearchSalaryModel searchSalaryModel);

	List<AllowanceEntity> getAllowance(int staff_id);

	List<TimeEntity> getTimeOfUserByMonthYear(SearchSalaryModel searchSalaryModel);

	void insertSalary(SalaryEntity saralyEntity);

	void deleteSalaryUser(SearchSalaryModel searchSalaryModel);

}
