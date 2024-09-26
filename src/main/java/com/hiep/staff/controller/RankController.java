package com.hiep.staff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiep.staff.entity.AbsentEntity;
import com.hiep.staff.entity.ClassRankDetailEntity;
import com.hiep.staff.entity.StudentAbsenceStatisticsEntity;
import com.hiep.staff.entity.StudentScoreDetailsEntity;
import com.hiep.staff.mapper.AbsentMapper;
import com.hiep.staff.mapper.ClassMapper;
import com.hiep.staff.mapper.LogClassMapper;
import com.hiep.staff.mapper.RankMapper;
import com.hiep.staff.mapper.ScoreMapper;
import com.hiep.staff.mapper.StudentMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class RankController {

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
	
	@Autowired
	RankMapper rankMapper;

	/**
	 * 
	 * LOG CLASS
	 * 
	 * */
	@GetMapping("/statistics")
    public ResponseEntity<List<ClassRankDetailEntity>> getClassStatistics() {
        return ResponseEntity.ok(rankMapper.getClassStatistics());
    }
	
	@GetMapping("/absence-statistics")
    public List<StudentAbsenceStatisticsEntity> getStudentAbsenceStatistics(@RequestParam(required = false) Integer classId) {
        return rankMapper.getStudentAbsenceStatistics(classId);
    }
	
	// API lấy dữ liệu absent dựa trên class_id và student_id
    @GetMapping("/records")
    public List<AbsentEntity> getAbsentRecords(
        @RequestParam(value = "classId", required = false) Integer classId,
        @RequestParam(value = "studentId", required = false) Integer studentId
    ) {
        return absentMapper.getAbsentRecordsByClassAndStudent(classId, studentId);
    }
	
	@GetMapping("/score-statistics")
    public List<StudentAbsenceStatisticsEntity> getStudentScoreStatistics(@RequestParam(required = false) Integer classId) {
        return rankMapper.getStudentScoreStatistics(classId);
    }
	
	@GetMapping("/{studentId}/scores")
    public ResponseEntity<List<StudentScoreDetailsEntity>> getStudentScoreDetails(
            @PathVariable Integer studentId,
            @RequestParam Integer classId) {
        
        List<StudentScoreDetailsEntity> scoreDetails = rankMapper.getStudentScoreDetails(studentId, classId);
        
        if (scoreDetails.isEmpty()) {
            return ResponseEntity.noContent().build();  // Trả về 204 No Content nếu không có dữ liệu
        }
        
        return ResponseEntity.ok(scoreDetails);  // Trả về 200 OK cùng với dữ liệu
    }

	
	
}
