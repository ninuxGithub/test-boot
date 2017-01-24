package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import com.example.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentRepository studentRepo;

	@ResponseBody
	@RequestMapping("/findAll")
	public List<Student> findAllStudent() {
		return studentService.findAll();
	}

	@ResponseBody
	@RequestMapping("/findAllJpa")
	public List<Student> findAllStudentJpa() {
		return studentRepo.findAll();
	}

	@ResponseBody
	@RequestMapping("/likename")
	public List<Student> mybatisLikelime() {
		return studentService.likeName("小");
	}
	
	@ResponseBody
	@RequestMapping("/likename2")
	public List<Student> mybatisLikelime2() {
		return studentService.likeNameDefaultDataSource("小");
	}

}
