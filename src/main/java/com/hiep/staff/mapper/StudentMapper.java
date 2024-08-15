package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.StudentEntity;
import com.hiep.staff.model.ScoreModel;
import com.hiep.staff.model.StudentModel;

@Mapper
public interface StudentMapper {

	List<StudentEntity> getAllStudent(String classname);

	void insertStudent(StudentModel studentModel);
	
	Integer getStudentIdByNameAndClassname(ScoreModel scoreModel);
	
	Integer getCountStudenByName(StudentModel studentModel);
	
	Integer getCountStudent();

	Integer deleteStudentByNameAndClassname(StudentModel studentModel);
}
