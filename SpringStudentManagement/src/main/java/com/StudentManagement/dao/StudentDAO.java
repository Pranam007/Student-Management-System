package com.StudentManagement.dao;

import com.StudentManagement.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addStudent(Student student) {
        String sql = "INSERT INTO students (name, class, marks, gender) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
                student.getName(),
                student.getStudentClass(),
                student.getMarks(),
                student.getGender()
        );
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new StudentRowMapper());
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, class = ?, marks = ?, gender = ? WHERE student_id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                student.getName(),
                student.getStudentClass(),
                student.getMarks(),
                student.getGender(),
                student.getId()
        );
        return rowsAffected > 0;  // Return true if update was successful
    }

    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;  // Return true if deletion was successful
    }

    private static class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getInt("student_id"));
            student.setName(rs.getString("name"));
            student.setStudentClass(rs.getString("class"));
            student.setMarks(rs.getInt("marks"));
            student.setGender(rs.getString("gender"));
            return student;
        }
    }
}
