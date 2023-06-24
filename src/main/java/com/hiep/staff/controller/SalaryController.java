package com.hiep.staff.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiep.staff.entity.AccountsEntity;
import com.hiep.staff.entity.SalaryEntity;
import com.hiep.staff.entity.TimeEntity;
import com.hiep.staff.entity.WorkTotalEntity;
import com.hiep.staff.mapper.AccountsMapper;
import com.hiep.staff.mapper.SalaryMapper;
import com.hiep.staff.mapper.TimeMapper;
import com.hiep.staff.model.DateModel;
import com.hiep.staff.model.SearchSalaryModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/salary")
@CrossOrigin
public class SalaryController {

	@Autowired
	AccountsMapper accountsMapper;

	@Autowired
	SalaryMapper salaryMapper;

	@Autowired
	TimeMapper timeMapper;

	@PostMapping("get-salary-user")
	public List<SalaryEntity> getSalaryUser(@RequestBody SearchSalaryModel searchSalaryModel) {
		String yearMonth = searchSalaryModel.getYear() + "-" + searchSalaryModel.getMonth();
		searchSalaryModel.setYearMonth(yearMonth);

		int checkHasInserted = salaryMapper.checkHasInserted(searchSalaryModel);
		if (checkHasInserted > 0) {
			// Đã tồn tại dữ liệu
			List<SalaryEntity> result = salaryMapper.getSalaryUser(searchSalaryModel);
			return result;
		} else {
			// Chưa có dữ liệu, cần phải insert mới

			// lấy ra tài khoản có Staff id tương ứng
			List<AccountsEntity> searchAccountByStaffId = accountsMapper
					.searchAccountByStaffId(searchSalaryModel.getStaff_id());
			
			// Lấy ra toàn bộ thời gian trong tháng
			DateModel dateModel = new DateModel();
			dateModel.setMonth(searchSalaryModel.getMonth());
			dateModel.setYear(searchSalaryModel.getYear());
			dateModel.setStaff_id(searchSalaryModel.getStaff_id());
			List<TimeEntity> getTimeOfUser = timeMapper.searchTimeUser(dateModel);

			// tính tổng thời gian
			List<WorkTotalEntity> workTotals = timeMapper.sumTime(dateModel);
			
			int totalDayOfUser = timeMapper.searchTotalDayUser(dateModel);

			int minute = 0;
			int hour = 0;
			for (WorkTotalEntity workTotalEntity : workTotals) {
				String[] splitStrings;

				if (workTotalEntity == null) {
					splitStrings = "00:00".split(":");
				} else {
					splitStrings = workTotalEntity.getWork_total().split(":");
				}

				int getHour = Integer.valueOf(splitStrings[0]);
				int getMinute = Integer.valueOf(splitStrings[1]);
				hour += getHour;
				minute += getMinute;
			}

			int getHourFromMinute = minute / 60;
			int newHour = hour + getHourFromMinute;
			int newMinute = minute % 60;
//			WorkTotalEntity workTotalEntity = new WorkTotalEntity();
//			workTotalEntity.setHour(Integer.toString(newHour));
//			workTotalEntity.setMinute(Integer.toString(newMinute));
			String totalTimes = (Integer.toString(newHour).length() < 2 ? "0" + Integer.toString(newHour)
					: Integer.toString(newHour)) + ":"
					+ (Integer.toString(newMinute).length() < 2 ? "0" + Integer.toString(newMinute)
							: Integer.toString(newMinute));
			
			
			// total time up
			int hourUp = 0;
			int minuteUp = 0;
			for(TimeEntity timeEntity: getTimeOfUser) {
				String[] timeSplited = timeEntity.getTime_out().split(":");
				if(Integer.valueOf(timeSplited[0]) >= 22) {
					int resultHourUp = Integer.valueOf(timeSplited[0]) - 22;
					int resultMinuteUp = Integer.valueOf(timeSplited[1]);
					hourUp += resultHourUp;
					minuteUp += resultMinuteUp;
				}
			}
			
			int getHourUpFromMinute = minuteUp / 60;
			int newHourUp = hourUp + getHourUpFromMinute;
			int newMinuteUp = minuteUp % 60;
			
			// thời gian tăng ca
			String resultTotalTimeUp = (newHourUp < 10 ? '0' + String.valueOf(newHourUp) : String.valueOf(newHourUp))  + ":" + (newMinuteUp < 10 ? '0' + String.valueOf(newMinuteUp) : String.valueOf(newMinuteUp) );
			// thời gian bình thường (không tăng ca)
			int totalHourTimes = Integer.valueOf(totalTimes.split(":")[0]);
			int totalMinuteTimes = Integer.valueOf(totalTimes.split(":")[1]);
			
			int regularHour = totalHourTimes - newHourUp;
			int regularMinute = 0;
			if(totalMinuteTimes <= newMinuteUp) {
				regularMinute = (totalMinuteTimes + 60) - newMinuteUp;
				regularHour -= 1;
			}else if(totalMinuteTimes >= newMinuteUp) {
				regularMinute = totalMinuteTimes - newMinuteUp;
			}
			
			String resultRegularTime = (regularHour < 10 ? '0' + String.valueOf(regularHour) : String.valueOf(regularHour))  + ":" + (regularMinute < 10 ? '0' + String.valueOf(regularMinute) : String.valueOf(regularMinute) );
			
			// Tính tổng lương
			int luong_co_ban = searchAccountByStaffId.get(0).getBasic_salary();
			int luong_tang_ca = (int)(luong_co_ban * 1.25);
			int gio_thuong = regularHour;
			int phut_thuong = regularMinute;
			int gio_tang_ca = newHourUp;
			int phut_tang_ca = newMinuteUp;
			int tien_ho_tro = searchAccountByStaffId.get(0).getMoney_support();
			int luong_gio_thuong = gio_thuong * luong_co_ban;
			int luong_phut_thuong = phut_thuong * (luong_co_ban / 60);
			int luong_gio_tang_ca = gio_tang_ca * luong_tang_ca;
			int luong_phut_tang_ca = phut_tang_ca * (luong_tang_ca / 60);
			
			int salary = luong_gio_thuong + luong_phut_thuong + luong_gio_tang_ca + luong_phut_tang_ca + tien_ho_tro;
			
			
			// Ngày hôm nay
			LocalTime localTime = LocalTime.now();
//			LocalTime localTime = LocalTime.of(23, 46,30);
			String hourString = Integer.toString(localTime.getHour());
			String minuteString = Integer.toString(localTime.getMinute());

			// Date
			LocalDate localDate = LocalDate.now();
//			LocalDate localDate = LocalDate.of(2023, 2, 9);
			String year = Integer.toString(localDate.getYear());
			String month = Integer.toString(localDate.getMonthValue());
			String day = Integer.toString(localDate.getDayOfMonth());
			
			if(hourString.length() < 2) {
				hourString = "0" + hourString;
			}
			if(minuteString.length() < 2) {
				minuteString = "0" + minuteString;
			}
			if(month.length() < 2) {
				month = "0" + month;
			}
			if(day.length() < 2) {
				day = "0" + day;
			}
			
			String today = year + "-" + month + "-" + day + " " + hourString + ":" + minuteString ;

			/**
			 * 
			 * 
			 * Kết quả
			 * 
			 **/

			SalaryEntity saralyEntity = new SalaryEntity();
			// Nếu không có dữ liệu (khônglàm ngày nào)
			if (totalDayOfUser == 0) {
				saralyEntity.setFullname(searchAccountByStaffId.get(0).getFullname());
				saralyEntity.setStaff_id(searchSalaryModel.getStaff_id());
				saralyEntity.setSalary_date(yearMonth);
				saralyEntity.setBasic_salary(Integer.toString(luong_co_ban));
				saralyEntity.setUp_salary(Integer.toString(luong_tang_ca));
				saralyEntity.setTotal_days(0);
				saralyEntity.setTotal_times("00:00");
				saralyEntity.setRegular_time("00:00");
				saralyEntity.setTotal_times_up("00:00");
				saralyEntity.setAllowance(0);
				saralyEntity.setSalary(0);
				saralyEntity.setCreate_at(today);	
			}else {
				saralyEntity.setFullname(searchAccountByStaffId.get(0).getFullname());
				saralyEntity.setStaff_id(searchSalaryModel.getStaff_id());
				saralyEntity.setSalary_date(yearMonth);
				saralyEntity.setBasic_salary(Integer.toString(luong_co_ban));
				saralyEntity.setUp_salary(Integer.toString(luong_tang_ca));
				saralyEntity.setTotal_days(totalDayOfUser);
				saralyEntity.setTotal_times(totalTimes);
				saralyEntity.setRegular_time(resultRegularTime);
				saralyEntity.setTotal_times_up(resultTotalTimeUp);
				saralyEntity.setAllowance(tien_ho_tro);
				saralyEntity.setSalary(salary);
				saralyEntity.setCreate_at(today);	
			}
			
			salaryMapper.insertSalary(saralyEntity);

			List<SalaryEntity> data = salaryMapper.getSalaryUser(searchSalaryModel);
			return data;
		}

	}
	
	@PostMapping("delete-salary-user")
	public void deleteSalaryUser(@RequestBody SearchSalaryModel searchSalaryModel) {
		String yearMonth = searchSalaryModel.getYear() + "-" + searchSalaryModel.getMonth();
		searchSalaryModel.setYearMonth(yearMonth);
		
		salaryMapper.deleteSalaryUser(searchSalaryModel);
	}

}
