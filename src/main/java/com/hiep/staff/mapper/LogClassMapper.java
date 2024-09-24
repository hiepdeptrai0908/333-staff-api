package com.hiep.staff.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.ClassLogDetails;
import com.hiep.staff.entity.LogClassEntity;
import com.hiep.staff.model.LogClassModel;

@Mapper
public interface LogClassMapper {
	int insertLogClass(LogClassModel logClassModel);

	int hasLogClass(LogClassModel logClassModel);

	int updateLogClass(LogClassModel logClassModel);

	int getLogIdByClassIdAndPartAndLogAt(LogClassModel logClassModel);

	List<ClassLogDetails> getClassLogDetails(Date log_at, Integer class_id, String part, String teacher);

	List<ClassLogDetails> findClassLogByLogAt(Date logAt);

	LogClassEntity getLogById(int id);

	void deleteLogClassById(int id);
}
