package com.hiep.staff.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.AbsentEntity;
import com.hiep.staff.model.AbsentModel;

@Mapper
public interface AbsentMapper {
	int insertAbsent(AbsentModel absentModel);

	int updateAbsent(AbsentModel absentModel);

	int hasAbsent(AbsentModel absentModel);

	List<AbsentEntity> findAllAbsent();

	List<AbsentEntity> findAbsentByClassIdAndPartAndAbsentAt(int classId, int logId, String part, Date logAt);

	List<AbsentEntity> findAbsentByLogId(int logId, int classId);

	void deleteAbsentByLogId(int log_id);
}
