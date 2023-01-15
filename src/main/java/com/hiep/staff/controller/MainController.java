package com.hiep.staff.controller;

import java.sql.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.TinyBitSet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiep.staff.entity.AccountsEntity;
import com.hiep.staff.entity.MessageEntity;
import com.hiep.staff.entity.TimeEntity;
import com.hiep.staff.entity.WorkTotalEntity;
import com.hiep.staff.mapper.AccountsMapper;
import com.hiep.staff.mapper.TimeMapper;
import com.hiep.staff.model.AccountsModel;
import com.hiep.staff.model.DateModel;
import com.hiep.staff.model.DeleteModel;
import com.hiep.staff.model.TimeModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin
public class MainController {

	@Autowired
	AccountsMapper accountsMapper;

	@Autowired
	TimeMapper timeMapper;

	// get all accounts
	@GetMapping("get-accounts")
	public List<AccountsEntity> getAccounts() {
		List<AccountsEntity> datas = accountsMapper.getAccounts();
		return datas;
	}

	// get account by user id
	@GetMapping("get-account/{id}")
	public List<AccountsEntity> getAccount(@PathVariable int id) {
		List<AccountsEntity> datas = accountsMapper.getAccount(id);
		return datas;
	}

	// get account by staff id
	@GetMapping("search-staff-id/{id}")
	public List<AccountsEntity> searchAccountByStaffId(@PathVariable int id) {
		List<AccountsEntity> datas = accountsMapper.searchAccountByStaffId(id);

		return datas;
	}

	// add new user
	@PostMapping("register-account")
	public HttpStatus registerAccount(@RequestBody AccountsModel accountsModel) {
		accountsMapper.registerUser(accountsModel);
		return HttpStatus.CREATED;
	}

	// search account
	@GetMapping("search-account/{value}")
	public List<AccountsEntity> searchAccount(@PathVariable int value) {
		List<AccountsEntity> datas = accountsMapper.searchAccount(value);
		return datas;
	}

	// login account
	@PostMapping("login")
	public List<AccountsEntity> login(@RequestBody AccountsModel accountsModel) {
		List<AccountsEntity> datas = accountsMapper.login(accountsModel);
		return datas;
	}

	// update user
	@PostMapping("update-account")
	public List<AccountsEntity> updateAccount(@RequestBody AccountsModel accountsModel) {
		accountsMapper.updateAccount(accountsModel);
		List<AccountsEntity> datas = accountsMapper.getAccount(accountsModel.getUser_id());
		return datas;
	}

	// delete user
	@PostMapping("delete-account")
	public List<AccountsEntity> deleteAccount(@RequestBody DeleteModel deleteModel) {
		accountsMapper.deleteAccount(deleteModel);
		List<AccountsEntity> datas = accountsMapper.getAccounts();

		return datas;
	}

	@PostMapping("check-user")
	public int checkUser(@RequestBody AccountsModel accountsModel) {
		int result = accountsMapper.checkUser(accountsModel);

		return result;
	}

	@PostMapping("check-staff-id")
	public int checkStaffId(@RequestBody AccountsModel accountsModel) {
		int result = accountsMapper.checkStaffId(accountsModel);

		return result;
	}

	@PostMapping("update-user")
	public void updateUser(@RequestBody AccountsModel accountsModel) {
		LocalDate localDate = LocalDate.now();
		String year = Integer.toString(localDate.getYear());
		String month = Integer.toString(localDate.getMonthValue());
		String day = Integer.toString(localDate.getDayOfMonth());
		if (year.length() < 2) {
			year = '0' + year;
		}
		if (month.length() < 2) {
			month = '0' + month;
		}
		if (day.length() < 2) {
			day = '0' + day;
		}

		String dateUpdate = year + "-" + month + "-" + day;
		accountsModel.setUpdate_at(dateUpdate);
		accountsMapper.updateUser(accountsModel);
	}

	@PostMapping("update-password")
	public void updatePassword(@RequestBody AccountsModel accountsModel) {
		LocalDate localDate = LocalDate.now();
		String year = Integer.toString(localDate.getYear());
		String month = Integer.toString(localDate.getMonthValue());
		String day = Integer.toString(localDate.getDayOfMonth());
		if (year.length() < 2) {
			year = '0' + year;
		}
		if (month.length() < 2) {
			month = '0' + month;
		}
		if (day.length() < 2) {
			day = '0' + day;
		}

		String dateUpdate = year + "-" + month + "-" + day;
		accountsModel.setUpdate_at(dateUpdate);
		accountsMapper.updatePassword(accountsModel);
	}
	
	@PostMapping("admin-login")
	public List<AccountsEntity> adminLogin() {
		List<AccountsEntity> result = accountsMapper.adminLogin();
		log.info("result:{}", result);
		return result;
	}

	/**
	 ** 
	 *
	 *
	 *
	 *
	 *
	 *
	 ** CHECK TIME
	 **
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 **/

	// get all list time
	@GetMapping("list-time")
	public List<TimeEntity> getAllTime() {
		List<TimeEntity> datas = timeMapper.getAllTime();
		return datas;
	}

	@PostMapping("get-time")
	public List<TimeEntity> getTimeByTimeId(@RequestBody TimeModel timeModel) {
		List<TimeEntity> datas = timeMapper.getTimeByTimeId(timeModel);
		return datas;
	}

	// 出勤
	@PostMapping("time-in")
	public MessageEntity timeIn(@RequestBody TimeModel timeModel) {

		// Time
		LocalTime localTime = LocalTime.now();
		String hour = Integer.toString(localTime.getHour());
		if (hour.length() < 2) {
			hour = '0' + hour;
		}
		int customMinute = localTime.getMinute();
		if (customMinute > 0 && customMinute < 15) {
			customMinute = 15;
		} else if (customMinute > 30 && customMinute < 45) {
			customMinute = 45;
		}

		String minute = Integer.toString(customMinute);
		if (minute.length() < 2) {
			minute = '0' + minute;
		}
//		
//		System.out.println("hour :" + hour);
//		
//		System.out.println("custome mimute :" + customMinute);
//		System.out.println("mimute :" + minute);

		// Date
		LocalDate localDate = LocalDate.now();
		String year = Integer.toString(localDate.getYear());
		String month = Integer.toString(localDate.getMonthValue());
		String day = Integer.toString(localDate.getDayOfMonth());
		if (year.length() < 2) {
			year = '0' + year;
		}
		if (month.length() < 2) {
			month = '0' + month;
		}
		if (day.length() < 2) {
			day = '0' + day;
		}

		TimeModel timeIn = new TimeModel();
		timeIn.setStaff_id(timeModel.getStaff_id());
		timeIn.setFullname(timeModel.getFullname());
		timeIn.setTime_in(hour + ":" + minute);
		timeIn.setDate_in(year + "-" + month + "-" + day);
		timeIn.setBreak_total("00:00");
		timeIn.setStatus("online");

		// check is online ?
		int checkCount = timeMapper.checking(timeModel.getStaff_id());
		if (checkCount > 0) {
			MessageEntity message = new MessageEntity();
			message.setTitle(timeModel.getFullname() + " chưa check out !");
			message.setStatus("error");
			return message;
		} else {
			MessageEntity message = new MessageEntity();
			message.setTitle(timeModel.getFullname() + " check in thành công.");
			message.setStatus("success");
			timeMapper.insertTimeIn(timeIn);
			return message;
		}
	}

	// 退勤
	@PostMapping("time-out")
	public MessageEntity timeOut(@RequestBody TimeModel timeModel) {
		
		
		log.info("datas:{}",timeModel);
		// Time
		LocalTime localTime = LocalTime.now();
		String minute = Integer.toString(localTime.getMinute());
		String hour = Integer.toString(localTime.getHour());
		if (minute.length() < 2) {
			minute = '0' + minute;
		}
		
		if (hour.length() < 2) {
			hour = '0' + hour;
		}

		
		if (localTime.getMinute() > 0 && localTime.getMinute() < 15) {
			minute = "00";
		}else if(localTime.getMinute() > 15 && localTime.getMinute() < 30) {
			minute = "15";
		}else if(localTime.getMinute() > 30 && localTime.getMinute() < 45) {
			minute = "30";
		}else if(localTime.getMinute() > 45 && localTime.getMinute() < 60) {
			minute = "45";
		}
		// Date
		LocalDate localDate = LocalDate.now();
		String year = Integer.toString(localDate.getYear());
		String month = Integer.toString(localDate.getMonthValue());
		String day = Integer.toString(localDate.getDayOfMonth());
		if (year.length() < 2) {
			year = '0' + year;
		}
		if (month.length() < 2) {
			month = '0' + month;
		}
		if (day.length() < 2) {
			day = '0' + day;
		}

		// kiểm tra có phải đang trong trạng thái check in hay không
		int checking = timeMapper.checking(timeModel.getStaff_id());
		if (checking > 0) {

			// caculate work time
			String getDateOneDay = timeMapper.getDateDay(timeModel.getStaff_id());
			String getTimeOneDay = timeMapper.getTimeDay(timeModel.getStaff_id());

			String[] splitDate = getDateOneDay.split("-");
			int dataYear = Integer.parseInt(splitDate[0]);
			int dataMonth = Integer.parseInt(splitDate[1]);
			int dataDay = Integer.parseInt(splitDate[2]);

			int nowYear = localDate.getYear();
			int nowMonth = localDate.getMonthValue();
			int nowDay = localDate.getDayOfMonth();

			String[] splitTime = getTimeOneDay.split(":");
			int dataHour = Integer.parseInt(splitTime[0]);
			int currentHour = localTime.getHour();

			int dataMinute = Integer.parseInt(splitTime[1]);
			int currentMinute = localTime.getMinute();
			
			if (localTime.getMinute() > 0 && localTime.getMinute() < 15) {
				currentMinute = 0;
			}else if(localTime.getMinute() > 15 && localTime.getMinute() < 30) {
				currentMinute = 15;
			}else if(localTime.getMinute() > 30 && localTime.getMinute() < 45) {
				currentMinute = 30;
			}else if(localTime.getMinute() > 45 && localTime.getMinute() < 60) {
				currentMinute = 45;
			}

			LocalDateTime timeDB = LocalDateTime.of(dataYear, dataMonth, dataDay, dataHour, dataMinute);
			LocalDateTime timeCurrent = LocalDateTime.of(nowYear, nowMonth, nowDay, currentHour, currentMinute);

			Duration timeElapsed = Duration.between(timeDB, timeCurrent);

			long totalMinuteWork = timeElapsed.getSeconds() / 60;

			String newHourWork = Long.toString(totalMinuteWork / 60);
			String newMinuteWork = Long.toString(totalMinuteWork % 60);
			if (newHourWork.length() < 2) {
				newHourWork = '0' + newHourWork;
			}
			if (totalMinuteWork / 60 > 23) {
				newHourWork = "23";
			}

			if (newMinuteWork.length() < 2) {
				newMinuteWork = '0' + newMinuteWork;
			}

			String resultWorkTime = newHourWork + ":" + newMinuteWork;

			// set work time
			timeModel.setWork_time(resultWorkTime);

			// Nếu đến muộn mà còn check out sớm
			if (dataHour == currentHour && currentMinute < dataMinute) {
				MessageEntity message = new MessageEntity();
				message.setTitle(
						timeModel.getFullname() + " đến muộn, cần đợi thêm vài phút nữa mới có thể check out !");
				message.setStatus("warning");
				return message;
			}

			timeModel.setTime_out(hour + ":" + minute);
			timeModel.setDate_out(year + "-" + month + "-" + day);
			timeModel.setStatus("offline");

			// kiểm tra xem có bắt đầu giải lao lần 1 hay không, có thì kiểm tra đã kết thúc
			// giải lao hay chưa
			int checkBreaking1 = timeMapper.checkBreaking1(timeModel.getStaff_id());

			if (checkBreaking1 > 0) {

				// kiểm tra đã nghỉ giải lao lần 1 hay chưa
				int checkHasBreakTime1 = timeMapper.checkHasBreakTime1(timeModel.getStaff_id());
				if (checkHasBreakTime1 > 0) {

					/**
					 * start total break time
					 */
					List<TimeEntity> getAllBreakTime = timeMapper.getAllBreakTime(timeModel.getStaff_id());
					String resultBreakTime1 = getAllBreakTime.get(0).getBreak_time1();
					String resultBreakTime2 = getAllBreakTime.get(0).getBreak_time2();

					// nếu không giải lao lần 2
					if (resultBreakTime2 == null) {
						resultBreakTime2 = "00:00";
					}

					String[] splitBreakTime1 = resultBreakTime1.split(":");
					String[] splitBreakTime2 = resultBreakTime2.split(":");

					// get hour and minute
					int hourBreakTime1 = Integer.valueOf(splitBreakTime1[0]);
					int minuteBreakTime1 = Integer.valueOf(splitBreakTime1[1]);

					int hourBreakTime2 = Integer.valueOf(splitBreakTime2[0]);
					int minuteBreakTime2 = Integer.valueOf(splitBreakTime2[1]);
					// handle splitBreakTime2

					int totalHourInt = hourBreakTime1 + hourBreakTime2;
					int totalMinuteInt = minuteBreakTime1 + minuteBreakTime2;

					if (totalMinuteInt >= 60) {
						totalMinuteInt = totalMinuteInt % 60;
						++totalHourInt;
					}

					String totalHour = Integer.toString(totalHourInt);
					String totalMinute = Integer.toString(totalMinuteInt);

					if (totalHour.length() < 2) {
						totalHour = "0" + totalHour;
					}
					if (totalMinute.length() < 2) {
						totalMinute = "0" + totalMinute;
					}

					String totalBreakTime = totalHour + ":" + totalMinute;
					/**
					 * total break time
					 */

					// set total break time in TimeModel
					timeModel.setBreak_total(totalBreakTime);

					/**
					 * work total = work_time - break_time
					 */

					LocalTime breakTime = LocalTime.of(totalHourInt, totalMinuteInt);
					LocalTime workTime = LocalTime.of(Integer.valueOf(newHourWork), Integer.valueOf(newMinuteWork));

					Duration WorkMinusBreak = Duration.between(breakTime, workTime);

					long minWorkMinusBreak = WorkMinusBreak.getSeconds() / 60;

					String newHourWorkMinusBreak = Long.toString(minWorkMinusBreak / 60);
					String newMinuteWorkMinusBreak = Long.toString(minWorkMinusBreak % 60);
					if (newHourWorkMinusBreak.length() < 2) {
						newHourWorkMinusBreak = '0' + newHourWorkMinusBreak;
					}

					if (newMinuteWorkMinusBreak.length() < 2) {
						newMinuteWorkMinusBreak = '0' + newMinuteWorkMinusBreak;
					}

					String resultWorkTotal = newHourWorkMinusBreak + ":" + newMinuteWorkMinusBreak;

					timeModel.setWork_total(resultWorkTotal);
					// end

					// kiểm tra đã vào giải lao lần 2 hay chưa
					int checkHasBreakIn2 = timeMapper.checkHasBreakIn2(timeModel.getStaff_id());
					if (checkHasBreakIn2 > 0) {
						// kiểm tra đã nghỉ giải lao lần 2 hay chưa
						int checkHasBreakTime2 = timeMapper.checkHasBreakTime2(timeModel.getStaff_id());
						if (checkHasBreakTime2 > 0) {
							MessageEntity message = new MessageEntity();
							message.setTitle(timeModel.getFullname() + " check out thành công.");
							message.setStatus("success");
							timeMapper.insertTimeOut(timeModel);
							return message;
						}
						MessageEntity message = new MessageEntity();
						message.setTitle(timeModel.getFullname() + " chưa kết thúc giải lao lần 2");
						message.setStatus("error");
						return message;
					}
					MessageEntity message = new MessageEntity();
					message.setTitle(timeModel.getFullname() + " check out thành công.");
					message.setStatus("success");
					timeMapper.insertTimeOut(timeModel);
					return message;

				} else {

					MessageEntity message = new MessageEntity();
					message.setTitle(timeModel.getFullname() + " chưa kết thúc giải lao");
					message.setStatus("error");
					return message;
				}
			} else {
				// không cần giải lao
				timeModel.setWork_total(resultWorkTime);

				MessageEntity message = new MessageEntity();
				message.setTitle(timeModel.getFullname() + " check out thành công.");
				message.setStatus("success");
				timeMapper.insertTimeOut(timeModel);
				return message;
			}

		} else {
			if (checking > 0) {

				MessageEntity message = new MessageEntity();
				message.setTitle(timeModel.getFullname() + " chưa bấm giải lao");
				message.setStatus("error");
				return message;
			} else {
				MessageEntity message = new MessageEntity();
				message.setTitle(timeModel.getFullname() + " chưa check in !");
				message.setStatus("error");
				return message;
			}
		}
	}

	// 休憩開始
	@PostMapping("break-in")
	public MessageEntity breakIn(@RequestBody TimeModel timeModel) {

		// Time
		LocalTime localTime = LocalTime.now();
		String hour = Integer.toString(localTime.getHour());
		if (hour.length() < 2) {
			hour = '0' + hour;
		}

		String minute = Integer.toString(localTime.getMinute());
		if (minute.length() < 2) {
			minute = '0' + minute;
		}

		String resultBreckIn = hour + ":" + minute;
		timeModel.setBreak_in1(resultBreckIn);
		timeModel.setBreak_in2(resultBreckIn);

		// kiểm tra có tài khoản trong trạng thái check in không
		int checkBreaking1 = timeMapper.checkBreaking1(timeModel.getStaff_id());
		if (checkBreaking1 > 0) {
			// bắt đầu giải lao 2
			// kiểm tra break1 đã nghỉ giải lao lần 1 hay chưa
			int checkHasBreakTime1 = timeMapper.checkHasBreakTime1(timeModel.getStaff_id());
			if (checkHasBreakTime1 > 0) {
				int checkHasBreakIn2 = timeMapper.checkHasBreakIn2(timeModel.getStaff_id());
				int checkHasBreakTime2 = timeMapper.checkHasBreakTime2(timeModel.getStaff_id());
				if (checkHasBreakIn2 > 0 && checkHasBreakTime2 == 0) {
					MessageEntity message = new MessageEntity();
					message.setTitle(timeModel.getFullname() + " đang vào giải lao lần 2 !");
					message.setStatus("error");
					return message;
				} else if (checkHasBreakTime2 > 0) {
					MessageEntity message = new MessageEntity();
					message.setTitle(timeModel.getFullname() + " đã hết lượt giải lao !");
					message.setStatus("error");
					return message;
				}
				MessageEntity message = new MessageEntity();
				message.setTitle(timeModel.getFullname() + " bắt đầu giải lao lần 2 thành công.");
				message.setStatus("success");
				timeMapper.insertBreakIn2(timeModel);
				return message;
			}
			MessageEntity message = new MessageEntity();
			message.setTitle(timeModel.getFullname() + " đang vào giải lao !");
			message.setStatus("error");
			return message;
		} else {
			int checking = timeMapper.checking(timeModel.getStaff_id());
			if (checking > 0) {
				/***
				 * 
				 * start check đi muộn đi muộn thì không được kyukei trước thời gian chỉ định
				 */
				String getTimeOneDay = timeMapper.getTimeDay(timeModel.getStaff_id());
				String[] splitTime = getTimeOneDay.split(":");
				int dataHour = Integer.parseInt(splitTime[0]);
				int currentHour = localTime.getHour();

				int dataMinute = Integer.parseInt(splitTime[1]);
				int currentMinute = localTime.getMinute();
				if (dataHour == currentHour && currentMinute < dataMinute) {
					MessageEntity message = new MessageEntity();
					message.setTitle(
							timeModel.getFullname() + " đến muộn, cần đợi thêm vài phút mới có thể giải lao !");
					message.setStatus("warning");
					return message;
				}
				/***
				 * 
				 * end check đi muộn
				 * 
				 */

				MessageEntity message = new MessageEntity();
				message.setTitle(timeModel.getFullname() + " bắt đầu giải lao thành công.");
				message.setStatus("success");
				timeMapper.insertBreakIn1(timeModel);
				return message;
			} else {
				MessageEntity message = new MessageEntity();
				message.setTitle(timeModel.getFullname() + " chưa check in !");
				message.setStatus("error");
				return message;
			}

		}
	}

	// 休憩終了
	@PostMapping("break-out")
	public MessageEntity breakOut(@RequestBody TimeModel timeModel) {

		// Time
		LocalTime localTime = LocalTime.now();
		String hour = Integer.toString(localTime.getHour());
		if (hour.length() < 2) {
			hour = '0' + hour;
		}

		String minute = Integer.toString(localTime.getMinute());
		if (minute.length() < 2) {
			minute = '0' + minute;
		}

		// check is Breaking ?
		int checkBreaking1 = timeMapper.checkBreaking1(timeModel.getStaff_id());
		if (checkBreaking1 > 0) {

			// caculate time date break1
			// start
			String getTimeBreakOneDay1 = timeMapper.getTimeBreakOneDay1(timeModel.getStaff_id());
			String[] splitTime1 = getTimeBreakOneDay1.split(":");
			int dataHour1 = Integer.parseInt(splitTime1[0]);
			int currentHour1 = localTime.getHour();

			int dataMinute1 = Integer.parseInt(splitTime1[1]);
			int currentMinute1 = localTime.getMinute();

			LocalTime hourDB1 = LocalTime.of(dataHour1, dataMinute1);
			LocalTime hourPresen1 = LocalTime.of(currentHour1, currentMinute1);

			Duration timeElapsed1 = Duration.between(hourDB1, hourPresen1);

			long totalMinuteBreak1 = timeElapsed1.getSeconds() / 60;

			String newHourBreak1 = Long.toString(totalMinuteBreak1 / 60);
			String newMinuteBreak1 = Long.toString(totalMinuteBreak1 % 60);
			if (newHourBreak1.length() < 2) {
				newHourBreak1 = '0' + newHourBreak1;
			}

			if (newMinuteBreak1.length() < 2) {
				newMinuteBreak1 = '0' + newMinuteBreak1;
			}

			String resultBreakTime1 = newHourBreak1 + ":" + newMinuteBreak1;
			// end

			// set break time
			timeModel.setBreak_time1(resultBreakTime1);

			timeModel.setBreak_out1(hour + ":" + minute);

			// kiêmt tra đã thoát giải lao lần 1 hay chưa
			int checkHasBreakTime1 = timeMapper.checkHasBreakTime1(timeModel.getStaff_id());
			if (checkHasBreakTime1 > 0) {

				// kiểm tra đã bấm giải lao lần 2 hay chưa
				int checkHasBreakIn2 = timeMapper.checkHasBreakIn2(timeModel.getStaff_id());
				if (checkHasBreakIn2 > 0) {

					// caculate time break time 2
					// start
					String getTimeBreakOneDay2 = timeMapper.getTimeBreakOneDay2(timeModel.getStaff_id());
					System.out.println("break_in2:" + getTimeBreakOneDay2);
					String[] splitTime2 = getTimeBreakOneDay2.split(":");
					int dataHour2 = Integer.parseInt(splitTime2[0]);
					int currentHour2 = localTime.getHour();

					int dataMinute2 = Integer.parseInt(splitTime2[1]);
					int currentMinute2 = localTime.getMinute();

					LocalTime hourDB2 = LocalTime.of(dataHour2, dataMinute2);
					LocalTime hourPresen2 = LocalTime.of(currentHour2, currentMinute2);

					Duration timeElapsed2 = Duration.between(hourDB2, hourPresen2);

					long totalMinuteBreak2 = timeElapsed2.getSeconds() / 60;

					String newHourBreak2 = Long.toString(totalMinuteBreak2 / 60);
					String newMinuteBreak2 = Long.toString(totalMinuteBreak2 % 60);
					if (newHourBreak2.length() < 2) {
						newHourBreak2 = '0' + newHourBreak2;
					}

					if (newMinuteBreak2.length() < 2) {
						newMinuteBreak2 = '0' + newMinuteBreak2;
					}

					String resultBreakTime2 = newHourBreak2 + ":" + newMinuteBreak2;
					// end

					// set break time
					timeModel.setBreak_time2(resultBreakTime2);

					timeModel.setBreak_out2(hour + ":" + minute);
					int checkHasBreakTime2 = timeMapper.checkHasBreakTime2(timeModel.getStaff_id());
					if (checkHasBreakTime2 > 0) {
						MessageEntity message = new MessageEntity();
						message.setTitle(timeModel.getFullname() + " đã nghỉ giải lao lần 2 !");
						message.setStatus("error");
						return message;
					}
					MessageEntity message = new MessageEntity();
					message.setTitle(timeModel.getFullname() + " nghỉ giải lao lần 2 thành công.");
					message.setStatus("success");
					timeMapper.insertBreakOut2(timeModel);
					return message;
				} else {
					MessageEntity message = new MessageEntity();
					message.setTitle(timeModel.getFullname() + " chưa bấm giải lao lần 2 !");
					message.setStatus("error");
					return message;
				}
			}
			MessageEntity message = new MessageEntity();
			message.setTitle(timeModel.getFullname() + " nghỉ giải lao thành công.");
			message.setStatus("success");
			timeMapper.insertBreakOut1(timeModel);
			return message;
		} else {

			int checking = timeMapper.checking(timeModel.getStaff_id());
			if (checking > 0) {

				MessageEntity message = new MessageEntity();
				message.setTitle(timeModel.getFullname() + " chưa bấm bắt đầu giải lao !");
				message.setStatus("error");
				return message;
			} else {
				MessageEntity message = new MessageEntity();
				message.setTitle(timeModel.getFullname() + " chưa check in !");
				message.setStatus("error");
				return message;
			}
		}
	}

	// Query thời gian của hôm nay
	@GetMapping("today")
	public List<TimeEntity> getToday() {
		LocalDate date = LocalDate.now();
		String getYear = Integer.toString(date.getYear());
		String getMonth = Integer.toString(date.getMonthValue());
		String getDay = Integer.toString(date.getDayOfMonth());

		if (getYear.length() < 2) {
			getYear = '0' + getYear;
		}
		if (getMonth.length() < 2) {
			getMonth = '0' + getMonth;
		}
		if (getDay.length() < 2) {
			getDay = '0' + getDay;
		}

		String today = getYear + "-" + getMonth + "-" + getDay;
		List<TimeEntity> datas = timeMapper.getToday(today);
		return datas;
	}

	// search time by date
	@PostMapping("/time/search")
	public List<TimeEntity> searchTimeByDate(@RequestBody DateModel dateModel) {
		List<TimeEntity> datas = timeMapper.searchTimeByDate(dateModel);
		return datas;
	}

	// Query user đang online
	@GetMapping("online")
	public List<TimeEntity> getStatusOnline() {
		List<TimeEntity> datas = timeMapper.getStatusOnline();
		return datas;
	}

	@PostMapping("update-time")
	public List<TimeEntity> updateTime(@RequestBody TimeModel timeModel) {

		// date
		LocalDate localDate = LocalDate.now();
		String year = Integer.toString(localDate.getYear());
		String month = Integer.toString(localDate.getMonthValue());
		String day = Integer.toString(localDate.getDayOfMonth());
		if (year.length() < 2) {
			year = '0' + year;
		}
		if (month.length() < 2) {
			month = '0' + month;
		}
		if (day.length() < 2) {
			day = '0' + day;
		}

		String dateUpdate = year + "-" + month + "-" + day;
		timeModel.setUpdate_time(dateUpdate);

		// time
		LocalTime localTime = LocalTime.now();
		String hour = Integer.toString(localTime.getHour());
		if (hour.length() < 2) {
			hour = '0' + hour;
		}

		String minute = Integer.toString(localTime.getMinute());
		if (minute.length() < 2) {
			minute = '0' + minute;
		}

		String timeUpdate = hour + ":" + minute;
		timeModel.setUpdate_date(dateUpdate);
		timeModel.setUpdate_time(timeUpdate);

		int isChangeBreakTotal = timeMapper.checkIsChangeBreakTotal(timeModel);
		if (isChangeBreakTotal == 0) {
			timeMapper.updateWhenIsChangeBreakTotal(timeModel);
		} else if (isChangeBreakTotal == 1) {
			timeMapper.updateWhenNotChangeBreakTotal(timeModel);
		} else {
			log.info("message update: Dữ liệu có vấn đề !");
		}

		List<TimeEntity> datas = timeMapper.getTimeByTimeId(timeModel);
		return datas;
	}

	@GetMapping("delete-time/{id}")
	public void deleteTime(@PathVariable Number id) {
		timeMapper.deleteTime(id);
	}

	@PostMapping("search-time-user")
	public List<TimeEntity> searchTimeUser(@RequestBody DateModel dateModel) {
		List<TimeEntity> datas = timeMapper.searchTimeUser(dateModel);

		return datas;
	}

	@PostMapping("total-month-time")
	public WorkTotalEntity totalMonthTime(@RequestBody DateModel dateModel) {
		List<WorkTotalEntity> workTotals = timeMapper.sumTime(dateModel);

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
		WorkTotalEntity workTotalEntity = new WorkTotalEntity();
		workTotalEntity.setHour(Integer.toString(newHour));
		workTotalEntity.setMinute(Integer.toString(newMinute));
		workTotalEntity.setWork_total(
				(Integer.toString(newHour).length() < 2 ? "0" + Integer.toString(newHour) : Integer.toString(newHour))
						+ ":" + (Integer.toString(newMinute).length() < 2 ? "0" + Integer.toString(newMinute)
								: Integer.toString(newMinute)));
		return workTotalEntity;
	}
	
	@PostMapping("total-month-all")
	public WorkTotalEntity totalMonthAll(@RequestBody DateModel dateModel) {
		List<WorkTotalEntity> workTotals = timeMapper.totalMonthAll(dateModel);

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
		WorkTotalEntity workTotalEntity = new WorkTotalEntity();
		workTotalEntity.setHour(Integer.toString(newHour));
		workTotalEntity.setMinute(Integer.toString(newMinute));
		workTotalEntity.setWork_total(
				(Integer.toString(newHour).length() < 2 ? "0" + Integer.toString(newHour) : Integer.toString(newHour))
						+ ":" + (Integer.toString(newMinute).length() < 2 ? "0" + Integer.toString(newMinute)
								: Integer.toString(newMinute)));
		return workTotalEntity;
	}

	@GetMapping("example")
	public void example(@RequestBody TimeModel timeModel) {
//		int dataHour = 11;
//		int dataMinute = 35;
//
//		int currentHour = 12;
//		int currentMinute = 2;
//
//		LocalTime hourDB = LocalTime.of(dataHour, dataMinute);
//		LocalTime hourPresen = LocalTime.of(currentHour, currentMinute);
//
//		Duration timeElapsed = Duration.between(hourDB, hourPresen);
//
//		long totalMinuteWork = timeElapsed.getSeconds() / 60;
//
//		String newHourWork = Long.toString(totalMinuteWork / 60);
//		String newMinuteWork = Long.toString(totalMinuteWork % 60);
//		if (newHourWork.length() < 2) {
//			newHourWork = '0' + newHourWork;
//		}
//
//		if (newMinuteWork.length() < 2) {
//			newMinuteWork = '0' + newMinuteWork;
//		}
//
//		String resultWorkTime = newHourWork + ":" + newMinuteWork;
		List<TimeEntity> getAllBreakTime = timeMapper.getAllBreakTime(timeModel.getStaff_id());
		String resultBreakTime1 = getAllBreakTime.get(0).getBreak_time1();
		String resultBreakTime2 = getAllBreakTime.get(0).getBreak_time2();
		if (resultBreakTime2 == null) {
			resultBreakTime2 = "00:00";
		}

		String[] splitBreakTime1 = resultBreakTime1.split(":");
		String[] splitBreakTime2 = resultBreakTime2.split(":");

		// get hour and minute
		int hourBreakTime1 = Integer.valueOf(splitBreakTime1[0]);
		int minuteBreakTime1 = Integer.valueOf(splitBreakTime1[1]);

		int hourBreakTime2 = Integer.valueOf(splitBreakTime2[0]);
		int minuteBreakTime2 = Integer.valueOf(splitBreakTime2[1]);
		// handle splitBreakTime2

		String totalHour = Integer.toString(hourBreakTime1 + hourBreakTime2);
		String totalMinute = Integer.toString(minuteBreakTime1 + minuteBreakTime2);

		if (totalHour.length() < 2) {
			totalHour = "0" + totalHour;
		}
		if (totalMinute.length() < 2) {
			totalMinute = "0" + totalMinute;
		}

		String totalBreakTime = totalHour + ":" + totalMinute;

		log.info("totalBreakTime:{}", totalBreakTime);

	}

}
