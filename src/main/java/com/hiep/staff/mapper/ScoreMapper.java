package com.hiep.staff.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.ScoreEntity;
import com.hiep.staff.model.ScoreModel;


@Mapper
public interface ScoreMapper {

	void insertScore(ScoreModel scoreModel);
	
	List<ScoreEntity> getScoreByClassnameAndLesson(ScoreModel scoreModel);
	
	int getMaxLesson();
	
	int checkLesson(int lesson);
	
	int updateScore(ScoreModel scoreModel);

	boolean hasTestLesson(ScoreModel scoreModel);
}
