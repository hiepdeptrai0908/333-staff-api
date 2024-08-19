package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.StudentEntity;
import com.hiep.staff.model.ScoreModel;
import com.hiep.staff.model.StudentModel;

@Mapper
public interface StudentMapper {

	List<StudentEntity> getAllStudentByClassId(int class_id);
	
	List<StudentEntity> getStudentScoresByStudentIdAndClassId(StudentModel studentModel);

	void insertStudent(StudentModel studentModel);
	
	String getNameByStudentId(StudentModel studentModel);
	
	Integer getCountStudenByName(StudentModel studentModel);
	
	Integer getCountStudent();

	Integer deleteStudentByNameAndClassId(StudentModel studentModel);
}
