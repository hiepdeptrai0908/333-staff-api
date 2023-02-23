package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.TimeEntity;
import com.hiep.staff.entity.WorkTotalEntity;
import com.hiep.staff.model.DateModel;
import com.hiep.staff.model.TimeModel;

@Mapper
public interface TimeMapper {

	List<TimeEntity> getAllTime();

	int insertTimeIn(TimeModel timeModel);
	
	int insertTimeOut(TimeModel timeModel);

	int checking(int staff_id);

	String getTimeDay(int staff_id);
	String getDateDay(int staff_id);
	

	String getTimeBreakOneDay1(int staff_id);
	
	int checkBreaking1(int staff_id);

	int insertBreakIn1(TimeModel timeModel);
	
	int insertBreakOut1(TimeModel timeModel);
	
	int checkBreakLast1(int staff_id);


	// break 2
	int checkHasBreakTime1(int staff_id);
	
	int insertBreakIn2(TimeModel timeModel);

	int checkHasBreakIn2(int staff_id);

	int insertBreakOut2(TimeModel timeModel);

	String getTimeBreakOneDay2(int staff_id);

	int checkHasBreakTime2(int staff_id);
	
	
	// break total
	List<TimeEntity> getAllBreakTime(int staff_id);

	List<TimeEntity> getToday(String today);

	List<TimeEntity> getStatusOnline();

	List<TimeEntity> searchTimeByDate(DateModel dateModel);

	List<TimeEntity> getTimeByTimeId(TimeModel timeModel);

	int checkIsChangeBreakTotal(TimeModel timeModel);

	void updateWhenIsChangeBreakTotal(TimeModel timeModel);

	void updateWhenNotChangeBreakTotal(TimeModel timeModel);

	void deleteTime(Number id);

	List<TimeEntity> searchTimeUser(DateModel dateModel);
	
	int searchTotalDayUser(DateModel dateModel);

	List<WorkTotalEntity> sumTime(DateModel dateModel);

	List<WorkTotalEntity> totalMonthAll(DateModel dateModel);

	
}
