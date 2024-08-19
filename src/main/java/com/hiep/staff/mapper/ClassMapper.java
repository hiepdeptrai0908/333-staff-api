package com.hiep.staff.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.GetMapping;

import com.hiep.staff.entity.ClassEntity;
import com.hiep.staff.model.ClassModel;


@Mapper
public interface ClassMapper {
	int insertClass(String classname);
	
	List<ClassEntity> getAllClass();
	
	String getClassnameById(int id);
	
	int updateClass(ClassModel classModel);
	
	int deleteClass(int id);

	int hasClassname(String classname);
}
