package com.hiep.staff.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiep.staff.entity.ScoreEntity;
import com.hiep.staff.entity.StudentEntity;
import com.hiep.staff.mapper.ScoreMapper;
import com.hiep.staff.mapper.StudentMapper;
import com.hiep.staff.model.ScoreModel;
import com.hiep.staff.model.StudentModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin
public class StudentController {

	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	ScoreMapper scoreMapper;

	/**
	 * 
	 * STUDENT
	 * 
	 * */
	// thêm học sinh mới
	@PostMapping("/student")
	public String insertStudent(@RequestBody StudentModel studentModel) {
		// Kiểm tra xem tên học sinh có bị trùng hay không
		int checkStudentName = studentMapper.getCountStudenByName(studentModel.getName());
		if (checkStudentName == 0) {
			studentMapper.insertStudent(studentModel);
			return "Đã thêm " + studentModel.getName() + " vào lớp " + studentModel.getClassname();
		}else {
			return "Tên " + studentModel.getName() + " đã tồn tại ở lớp " + studentModel.getClassname() + " !";
		}
	}
	
	// Lấy ra tất cả học sinh
	@GetMapping("/students")
	public List<StudentEntity> getAllStudents() {
		return studentMapper.getAllStudent();
	}
	
	/**
	 * 
	 * SCORE
	 * 
	 * */
	// nhập điểm
		@PostMapping("/score")
		public String insertScore(@RequestBody List<ScoreModel> scores) {
			
			// kiểm tra xem đã có lesson hay chưa rồi mới insert
			int countLesson = scoreMapper.checkLesson(scores.get(1).getLesson());
			System.out.println("countLesson:" +countLesson);
			if (countLesson == 0) {
				for (ScoreModel score : scores) {
					int studentId = studentMapper.getStudentIdByName(score.getName());
					if (score.getScore() == 0) {
						score.setError(0);
					}else {
						int errorScore = 50 - score.getScore();
						score.setError(errorScore);
					}
					score.setStudent_id(studentId);
					scoreMapper.insertScore(score);
				}
				return "Nhập điểm bài " + scores.get(1).getLesson() + " đã xong...";
			}else {
				return "Điểm bài " + scores.get(1).getLesson() + " đã tồn tại !";
			}
		}
		
		@PostMapping("/score-lesson")
		public List<ScoreEntity> getScoreByClassnameAndLesson(@RequestBody ScoreModel scoreModel){
			List<ScoreEntity> listScore = scoreMapper.getScoreByClassnameAndLesson(scoreModel);
			return listScore;
		}
		
		@GetMapping("/max-lesson")
		public int getMaxLesson() {
			int result = scoreMapper.getMaxLesson();
			return result;
		}
		
		@PutMapping("/score")
		public String updateScore(@RequestBody ScoreModel scoreModel) {
			
			// Nếu đã tồn tại bài kiểm tra thì update, nếu chưa từng kiểm tra thì sẽ thêm mới
			boolean hasTestLesson = scoreMapper.hasTestLesson(scoreModel);
			if (hasTestLesson) {
				int studentId = studentMapper.getStudentIdByName(scoreModel.getName());
				scoreModel.setStudent_id(studentId);
				int result = scoreMapper.updateScore(scoreModel);
				if (scoreModel.getScore() == 0) {
					scoreModel.setError(0);
				}else {
					scoreModel.setError(50 - scoreModel.getScore());
				}
				System.out.println("result: " + result);
			}else {
				int studentId = studentMapper.getStudentIdByName(scoreModel.getName());
				if (scoreModel.getScore() == 0) {
					scoreModel.setError(0);
				}else {
					int errorScore = 50 - scoreModel.getScore();
					scoreModel.setError(errorScore);
				}
				scoreModel.setStudent_id(studentId);
				scoreMapper.insertScore(scoreModel);
			}
			
			
			return "Đã cập nhật điểm cho " + scoreModel.getName() + " bài " + scoreModel.getLesson() + " với điểm là: " + scoreModel.getScore();
		}

}
