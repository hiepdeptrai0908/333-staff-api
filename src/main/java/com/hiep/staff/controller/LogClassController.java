package com.hiep.staff.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiep.staff.entity.AbsentEntity;
import com.hiep.staff.entity.ClassLogDetails;
import com.hiep.staff.entity.LogClassEntity;
import com.hiep.staff.entity.MessageEntity;
import com.hiep.staff.mapper.AbsentMapper;
import com.hiep.staff.mapper.ClassMapper;
import com.hiep.staff.mapper.LogClassMapper;
import com.hiep.staff.mapper.ScoreMapper;
import com.hiep.staff.mapper.StudentMapper;
import com.hiep.staff.model.AbsentModel;
import com.hiep.staff.model.LogClassModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class LogClassController {

	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	ScoreMapper scoreMapper;
	
	@Autowired
	ClassMapper classMapper;
	
	@Autowired
	LogClassMapper logClassMapper;
	
	@Autowired
	AbsentMapper absentMapper;

	/**
	 * 
	 * LOG CLASS
	 * 
	 * */
	
	@GetMapping("/log-class")
	public List<ClassLogDetails> findAllClassLog(
	    @RequestParam(required = false) Date log_at,
	    @RequestParam(required = false) Integer class_id,
	    @RequestParam(required = false) String part,
	    @RequestParam(required = false) String teacher) {
		
	    if (class_id != null && !class_id.equals("null")) {
	        class_id = Integer.valueOf(class_id);
	    }

	    List<ClassLogDetails> classLogDetails = logClassMapper.getClassLogDetails(log_at, class_id, part, teacher);
	    for (ClassLogDetails classLogDetail : classLogDetails) {
	        int logId = classLogDetail.getLog_id();
	        int classId = classLogDetail.getClass_id();

	        List<AbsentEntity> findAllAbsent = absentMapper.findAbsentByLogId(logId, classId);
	        classLogDetail.setAbsentees(findAllAbsent);
	    }
	    return classLogDetails;
	}

	
	@PostMapping("/log-class")
	public MessageEntity insertLogClass(@RequestBody LogClassModel logClassModel) {
		
		int classSize = classMapper.getClassSizeByClassId(logClassModel.getClass_id());
		logClassModel.setClass_size(classSize);
		
		String classname = classMapper.getClassnameById(logClassModel.getClass_id());
		MessageEntity message = new MessageEntity();
		
		int hasLogClass = logClassMapper.hasLogClass(logClassModel);
		if (hasLogClass > 0) {
			int resultLog = logClassMapper.updateLogClass(logClassModel);
			
			if (resultLog > 0) {
				int logId = logClassMapper.getLogIdByClassIdAndPartAndLogAt(logClassModel);
				absentMapper.deleteAbsentByLogId(logId);
				message.setTitle("Đã cập nhật xong sổ đầu bài lớp " + classname + " " + logClassModel.getPart() + " ngày " + logClassModel.getLog_at().getDayOfMonth() + " tháng " + logClassModel.getLog_at().getMonthValue());
				message.setStatus(String.valueOf(logId));
			}else {
				message.setTitle("Đã có lỗi khi cập nhật sổ đầu bài !");
				message.setStatus("error");
			}
		}else {
			int resultLog = logClassMapper.insertLogClass(logClassModel);
			if (resultLog > 0) {
				int logId = logClassMapper.getLogIdByClassIdAndPartAndLogAt(logClassModel);
				message.setTitle("Đã nhập xong sổ đầu bài lớp " + classname + " " + logClassModel.getPart() + " ngày " + logClassModel.getLog_at().getDayOfMonth() + " tháng " + logClassModel.getLog_at().getMonthValue());
				message.setStatus(String.valueOf(logId));
			}else {
				message.setTitle("Đã có lỗi khi nhập sổ đầu bài !");
				message.setStatus("error");
			}
		}
		return message;
	}
	
	@DeleteMapping("/log-class/{id}")
	public MessageEntity deleteLogClassById(@PathVariable int id) {
		LogClassEntity logClass = logClassMapper.getLogById(id);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = logClass.getLog_at().format(formatter);
        
		String classname = classMapper.getClassnameById(logClass.getClass_id());
		logClassMapper.deleteLogClassById(id);
		
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setTitle("Đã xoá bản ghi lớp " + classname + " ngày " + formattedDate);
		messageEntity.setStatus("Success");
		return messageEntity;
	}
	
	/**
	 * 
	 * ABSENT
	 * 
	 * */
	
	@PostMapping("/absent")
	public void insertAbsent(@RequestBody List<AbsentModel> absentModels) {
		
		for (AbsentModel absentModel : absentModels) {
			int hasAbsent = absentMapper.hasAbsent(absentModel);
			if (hasAbsent > 0) {
				absentMapper.updateAbsent(absentModel);
				
			}else {
				absentMapper.insertAbsent(absentModel);
			}
		}
	}
	
	@GetMapping("/absent")
	public List<AbsentEntity> findAllAbsent() {
		return absentMapper.findAllAbsent();
	}
	
	

}
