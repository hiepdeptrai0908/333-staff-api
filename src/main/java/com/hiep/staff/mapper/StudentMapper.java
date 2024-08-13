package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.StudentEntity;
import com.hiep.staff.model.StudentModel;

@Mapper
public interface StudentMapper {

	List<StudentEntity> getAllStudent();

	void insertStudent(StudentModel studentModel);
	
	int getStudentIdByName(String name);
	
	int getCountStudenByName(String name);
	
	int getCountStudent();


}
