package com.hiep.staff.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.ScoreEntity;
import com.hiep.staff.model.ScoreModel;


@Mapper
public interface ScoreMapper {

	void insertScore(ScoreModel scoreModel);
	
	List<ScoreEntity> getScoreByClassnameAndLesson(ScoreModel scoreModel);
	
	Integer getMaxLesson(String classname);
	
	Integer checkLesson(int lesson);
	
	Integer updateScore(ScoreModel scoreModel);

	boolean hasTestLesson(ScoreModel scoreModel);
	
	Integer deleteByLesson(int lesson);
}
