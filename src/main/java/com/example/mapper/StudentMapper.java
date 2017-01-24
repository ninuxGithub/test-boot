package com.example.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.config.mybatis.MyMapper;
import com.example.entity.Student;

@Component
public interface StudentMapper extends MyMapper<Student> {
	List<Student> likeName(String name);

	Student getById(int id);

	int add(Student stu);

	String getNameById(int id);

}
