package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.SalaryEntity;
import com.hiep.staff.entity.TimeEntity;
import com.hiep.staff.model.SearchSalaryModel;

@Mapper
public interface SalaryMapper {

	List<SalaryEntity> getSalaryUser(SearchSalaryModel searchSalaryModel);

	int checkHasInserted(SearchSalaryModel searchSalaryModel);

	List<TimeEntity> getTimeOfUserByMonthYear(SearchSalaryModel searchSalaryModel);

	void insertSalary(SalaryEntity saralyEntity);

	void deleteSalaryUser(SearchSalaryModel searchSalaryModel);

}
