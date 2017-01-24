package com.example.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.example.datasource.TargetDataSource;
import com.example.entity.Student;
import com.example.mapper.StudentMapper;

@Service
public class StudentService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private StudentMapper studentMapper;

	@TargetDataSource(name = "ds1")
	public List<Student> likeName(String name) {
		return studentMapper.likeName(name);
	}
	
	
	public List<Student> likeNameDefaultDataSource(String name) {
		return studentMapper.likeName(name);
	}

	public List<Student> findAll() {
		String sql = "SELECT ID,NAME,SCORE_SUM,SCORE_AVG, AGE   FROM STUDENT";
		return jdbcTemplate.query(sql, new RowMapper<Student>() {

			@Override
			public Student mapRow(ResultSet set, int i) throws SQLException {
				Student s = new Student();
				s.setId(set.getInt("ID"));
				s.setName(set.getString("NAME"));
				s.setSumScore(set.getString("SCORE_SUM"));
				s.setAvgScore(set.getString("SCORE_AVG"));
				s.setAge(set.getInt("AGE"));
				return s;
			}
		});
	}
}
