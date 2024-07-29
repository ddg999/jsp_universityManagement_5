package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.GradeSemester;
import com.university.repository.interfaces.GradeRepository;
import com.university.util.DBUtil;

public class GradeRepositoryImpl implements GradeRepository {

	private static final String SELECT_GRADE_THIS_SEMESTER = " SELECT su.sub_year, su.semester, st.subject_id, su.name subject_name, pr.name professor_name, su.type,gr.grade, su.grades, RANK() OVER(ORDER BY gr.grade_value DESC) 'rank', ev.evaluation_id FROM stu_sub_tb AS st INNER JOIN subject_tb AS su ON st.subject_id = su.id INNER JOIN professor_tb AS pr ON su.professor_id = pr.id INNER JOIN grade_tb AS gr ON st.grade = gr.grade INNER JOIN student_tb AS stud on st.student_id = stud.id LEFT JOIN evaluation_tb AS ev ON st.subject_id = ev.subject_id WHERE st.student_id = ? AND su.semester = ? AND su.sub_year = ? ORDER BY st.subject_id ";
	private static final String SELECT_GRADE_SEMESTER_SEARCH = " SELECT su.sub_year, su.semester, st.subject_id, su.name subject_name, pr.name professor_name, su.type,gr.grade, su.grades, RANK() OVER(ORDER BY gr.grade_value DESC) 'rank', ev.evaluation_id FROM stu_sub_tb AS st INNER JOIN subject_tb AS su ON st.subject_id = su.id INNER JOIN professor_tb AS pr ON su.professor_id = pr.id INNER JOIN grade_tb AS gr ON st.grade = gr.grade INNER JOIN student_tb AS stud on st.student_id = stud.id LEFT JOIN evaluation_tb AS ev ON st.subject_id = ev.subject_id WHERE st.student_id = ? AND su.sub_year = ? AND su.semester = ? AND su.type like CONCAT('%', COALESCE(NULLIF(?, ''), su.type), '%') ORDER BY st.subject_id ";
	private static final String SELECT_GRADE_YEAR = " SELECT s.sub_year FROM stu_sub_tb ss JOIN subject_tb s ON s.id = ss.subject_id WHERE ss.student_id = ? GROUP BY sub_year ";
	private static final String COUNT_ALL_GRADE = " SELECT count(*) count FROM stu_sub_tb st INNER JOIN subject_tb su ON st.subject_id = su.id INNER JOIN student_tb stud on st.student_id = stud.id WHERE st.student_id = ? AND su.sub_year = ? AND su.semester = ? AND su.type = ? ";

	@Override
	public List<GradeSemester> getGradeThisSemester(int studentId, int semester, int year) {
		List<GradeSemester> gradeList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_GRADE_THIS_SEMESTER)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, semester);
			pstmt.setInt(3, year);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				gradeList.add(GradeSemester.builder().subYear(rs.getInt("sub_year")).semester(rs.getInt("semester"))
						.subjectId(rs.getInt("subject_id")).subjectName(rs.getString("subject_name"))
						.professorName(rs.getString("professor_name")).type(rs.getString("type"))
						.grade(rs.getString("grade")).grades(rs.getInt("grades")).rank(rs.getInt("rank"))
						.evaluationId(rs.getInt("evaluation_id")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gradeList;
	}

	@Override
	public List<GradeSemester> getGradeYear(int studentId) {
		List<GradeSemester> yearList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_GRADE_YEAR)) {
			pstmt.setInt(1, studentId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				yearList.add(GradeSemester.builder().subYear(rs.getInt("sub_year")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yearList;
	}

	@Override
	public int getTotalCountGrade(int studentId) {
		int count = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_GRADE)) {
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<GradeSemester> getGradeSemesterSearch(int studentId, int year, int semester, String type) {
		List<GradeSemester> gradeList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_GRADE_SEMESTER_SEARCH)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			pstmt.setString(4, type);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				gradeList.add(GradeSemester.builder().subYear(rs.getInt("sub_year")).semester(rs.getInt("semester"))
						.subjectId(rs.getInt("subject_id")).subjectName(rs.getString("subject_name"))
						.professorName(rs.getString("professor_name")).type(rs.getString("type"))
						.grade(rs.getString("grade")).grades(rs.getInt("grades")).rank(rs.getInt("rank"))
						.evaluationId(rs.getInt("evaluation_id")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gradeList;
	}

}
