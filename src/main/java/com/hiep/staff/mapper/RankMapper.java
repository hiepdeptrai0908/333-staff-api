package com.hiep.staff.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.ClassRankDetailEntity;
import com.hiep.staff.entity.StudentAbsenceStatisticsEntity;
import com.hiep.staff.entity.StudentScoreDetailsEntity;


@Mapper
public interface RankMapper {

	List<ClassRankDetailEntity> getClassStatistics();

	List<StudentAbsenceStatisticsEntity> getStudentAbsenceStatistics(Integer classId);

	List<StudentAbsenceStatisticsEntity> getStudentScoreStatistics(Integer classId);

	List<StudentScoreDetailsEntity> getStudentScoreDetails(Integer studentId, Integer classId);
}
