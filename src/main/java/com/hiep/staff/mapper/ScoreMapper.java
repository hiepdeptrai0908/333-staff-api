package com.hiep.staff.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.ScoreEntity;
import com.hiep.staff.model.ScoreModel;


@Mapper
public interface ScoreMapper {

	void insertScore(ScoreModel scoreModel);
	
	List<ScoreEntity> getScoreByClassIdAndLesson(ScoreModel scoreModel);
	
	List<ScoreEntity> getLessonByClassId(int class_id);
	
	Integer getMaxLesson(String classname);
	
	Integer checkLessonByClassIdAndLesson(ScoreModel scoreModel);
	
	Integer updateScore(ScoreModel scoreModel);

	boolean hasTestLesson(ScoreModel scoreModel);
	
	Integer deleteByClassIdAndLesson(ScoreModel scoreModel);

	List<ScoreEntity> getScoreByClassIdAndStudentIdAndLesson(ScoreModel scoreModel);
}
