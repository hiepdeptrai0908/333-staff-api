package com.hiep.staff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiep.staff.entity.ClassEntity;
import com.hiep.staff.entity.MessageEntity;
import com.hiep.staff.entity.ScoreEntity;
import com.hiep.staff.entity.StudentEntity;
import com.hiep.staff.mapper.ClassMapper;
import com.hiep.staff.mapper.ScoreMapper;
import com.hiep.staff.mapper.StudentMapper;
import com.hiep.staff.model.ClassModel;
import com.hiep.staff.model.ScoreModel;
import com.hiep.staff.model.StudentModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class StudentController {

	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	ScoreMapper scoreMapper;
	
	@Autowired
	ClassMapper classMapper;

	/**
	 * 
	 * STUDENT
	 * 
	 * */
	// thêm học sinh mới
	@PostMapping("/student")
	public String insertStudent(@RequestBody StudentModel studentModel) {
		// Kiểm tra xem tên học sinh có bị trùng hay không
		int checkStudentName = studentMapper.getCountStudenByName(studentModel);
		String classname = classMapper.getClassnameById(studentModel.getClass_id());
		if (checkStudentName == 0) {
			studentMapper.insertStudent(studentModel);
			return "Đã thêm " + studentModel.getName() + " vào lớp " + classname;
		}else {
			return "Tên " + studentModel.getName() + " đã tồn tại ở lớp " + classname + " !";
		}
	}
	
	// lấy ra tất cả học sinh
	@GetMapping("/students")
	public List<StudentEntity> getAllStudents(@RequestParam(required = false) Integer classId) {
	    return studentMapper.getAllStudents(classId);
	}
	
	// Lấy ra tất cả học sinh theo lớp
	@GetMapping("/class/{class_id}/students")
	public List<StudentEntity> getAllStudentsByClassId(@PathVariable int class_id) {
		return studentMapper.getAllStudentByClassId(class_id);
	}
	
	@PostMapping("/class/student")
	public List<StudentEntity> getStudentScoresByStudentIdAndClassId(@RequestBody StudentModel studentModel) {
		return studentMapper.getStudentScoresByStudentIdAndClassId(studentModel);
	}
	
	@DeleteMapping("/student")
	public String deleteStudentByNameAndClassname(@RequestBody StudentModel studentModel) {
		String name = studentMapper.getNameByClassIdAndStudentId(studentModel);
		studentModel.setName(name);
		int result = studentMapper.deleteStudentByNameAndClassId(studentModel);
		String classname = classMapper.getClassnameById(studentModel.getClass_id());
		if (result != 0) {
			return "Đã xoá học sinh tên: " + name + " của lớp " + classname;
		}else {
			return "Không thể xoá ... vì tên " + name + " ở lớp " + classname + " không tồn tại !";
		}
	}
	
	/**
	 * 
	 * SCORE
	 * 
	 * */
	// nhập điểm
		@PostMapping("/score")
		public MessageEntity insertScore(@RequestBody List<ScoreModel> scores) {
			
			// kiểm tra xem đã có lesson hay chưa rồi mới insert
			int countLesson = scoreMapper.checkLessonByClassIdAndLesson(scores.get(0));
			String classname = classMapper.getClassnameById(scores.get(0).getClass_id());
			MessageEntity mess = new MessageEntity();
			if (countLesson == 0) {
				for (ScoreModel score : scores) {
					if (score.getScore() == 0) {
						score.setError(0);
					}else {
						int errorScore = score.getMax_score() - score.getScore();
						score.setError(errorScore);
					}
					scoreMapper.insertScore(score);
				}
				mess.setStatus("success");
				mess.setTitle("Nhập điểm " + scores.get(0).getLesson_name() + " cho lớp " + classname + " đã xong...");
				return mess;
			}else {
				mess.setStatus("error");
				mess.setTitle("Thất bại... Điểm " + scores.get(0).getLesson_name() + " của lớp " + classname + " đã tồn tại !");
				return mess;
			}
		}
		
		@PostMapping("/score-lesson")
		public List<ScoreEntity> getScoreByClassIdAndLesson(@RequestBody ScoreModel scoreModel){
			List<ScoreEntity> listScore = scoreMapper.getScoreByClassIdAndLesson(scoreModel);
			return listScore;
		}
		
		@PostMapping("/score-student")
		public List<ScoreEntity> getScoreByClassIdAndStudentIdAndLesson(@RequestBody ScoreModel scoreModel) {
			int maxScore = scoreMapper.getMaxScoreBylesson(scoreModel);
			List<ScoreEntity> scoreByClassIdAndStudentIdAndLesson = scoreMapper.getScoreByClassIdAndStudentIdAndLesson(scoreModel);
			scoreByClassIdAndStudentIdAndLesson.get(0).setMax_score(maxScore);
			return scoreByClassIdAndStudentIdAndLesson;
		}
		
		@GetMapping("/max-lesson/{classname}")
		public int getMaxLesson(@PathVariable String classname) {
		    Integer result = scoreMapper.getMaxLesson(classname); // Dùng Integer thay vì int
		    if (result == 0) {
		        return 1; // Trả về giá trị mặc định nếu không có dữ liệu
		    }
		    return result;
		}
		
		@GetMapping("/class/{class_id}")
		public List<ScoreEntity> getLessonByClassId(@PathVariable int class_id) {
		    return scoreMapper.getLessonByClassId(class_id); // Dùng Integer thay vì int
		    
		}
		
		@PutMapping("/score")
		public String updateScore(@RequestBody ScoreModel scoreModel) {
			
			// Nếu đã tồn tại bài kiểm tra thì update, nếu chưa từng kiểm tra thì sẽ thêm mới
			boolean hasTestLesson = scoreMapper.hasTestLesson(scoreModel);
			
			StudentModel studentModel = new StudentModel();
			studentModel.setId(scoreModel.getStudent_id());
			studentModel.setClass_id(scoreModel.getClass_id());
			
			String name = studentMapper.getNameByClassIdAndStudentId(studentModel);
			if (hasTestLesson) {
				if (scoreModel.getScore() == 0) {
					scoreModel.setError(0);
				}else {
					scoreModel.setError(scoreModel.getMax_score() - scoreModel.getScore());
				}
				int result = scoreMapper.updateScore(scoreModel);
			}else {
				if (scoreModel.getScore() == 0) {
					scoreModel.setError(0);
				}else {
					int errorScore = scoreModel.getMax_score() - scoreModel.getScore();
					scoreModel.setError(errorScore);
				}
				scoreMapper.insertScore(scoreModel);
			}
			
			
			return "Đã cập nhật điểm cho " + name + ": " + scoreModel.getLesson_name() + " với điểm là " + scoreModel.getScore();
		}
		
		@DeleteMapping("/lesson")
		public String deleteByLesson(@RequestBody ScoreModel scoreModel) {
			String classname = classMapper.getClassnameById(scoreModel.getClass_id());
			int result = scoreMapper.deleteByClassIdAndLesson(scoreModel);
			if (result != 0) {
				return "Đã xoá " + scoreModel.getLesson_name() + " của cả lớp " + classname;
			}else {
				return "Không thể xoá ... vì điểm " + scoreModel.getLesson_name() + " của lớp " + classname + " không tồn tại !";
			}
		}
		
		/**
		 * 
		 * CLASS
		 * 
		 * */
		@PostMapping("/class")
		public String insertClass(@RequestBody ClassModel classModel) {
			int hasClassname = classMapper.hasClassname(classModel.getClassname());
			if(hasClassname > 0) {
				return "Không thể thêm lớp mới vì " + classModel.getClassname() + " đã tồn tại !";
			} else {
				classMapper.insertClass(classModel.getClassname());
				return "Đã thêm lớp " + classModel.getClassname() + " thành công.";
			}
			
		}
		
		@GetMapping("/class")
		public List<ClassEntity> getAllClass() {
			return classMapper.getAllClass();
		}
		
		@PutMapping("/class")
		public String updateClass(@RequestBody ClassModel classModel) {
			int hasClassname = classMapper.hasClassname(classModel.getClassname());
			
			if (hasClassname > 0) {
				return "Tên lớp không đúng hoặc không tồn tại!";
			}else {
				String currentClassname = classMapper.getClassnameById(classModel.getId());
				classMapper.updateClass(classModel);
				return "Đã sửa tên lớp " + currentClassname + " thành " + classModel.getClassname();
				
			}
		}
		
		@DeleteMapping("/class")
		public String deleteClass(@RequestBody ClassModel classModel) {
			String currentClassname = classMapper.getClassnameById(classModel.getId());
			int result = classMapper.deleteClass(classModel.getId());
			if (result > 0) {
				return "Đã xoá lớp " + currentClassname + " thành công";
			} else {
				return "Xoá lớp " + currentClassname + " thất bại!";
			}
		}

}
