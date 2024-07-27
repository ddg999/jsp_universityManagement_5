package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.university.model.Tuition;
import com.university.model.TuitionInfo;
import com.university.repository.interfaces.TuitionRepository;
import com.university.util.DBUtil;

public class TuitionRepositoryImpl implements TuitionRepository{

	private static final String INSERT_TUITION = " INSERT INTO tuition_tb (student_id, tui_year, semester, tui_amount, sch_type, sch_amount) VALUES (?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_TUITION_BY_ID_SQL = " select s.semester,c.name as cname ,d.name as dname, s.id , s.name , ctt.amount from college_tb as c join department_tb as d on c.id = d.college_id join student_tb as s on d.id = s.dept_id join coll_tuit_tb as ctt on c.id = ctt.college_id where s.id = ? ";
	private static final String SELECT_TUITION_BY_ID_SEMESTER_SQL = " select s.id, ct.amount from student_tb as s join department_tb as d on s.dept_id = d.id join college_tb as c on c.id = d.college_id join coll_tuit_tb as ct on c.id = ct.college_id where s.id = ? and s.semester = ? ";
	
	@Override
	public List<Tuition> getAllTuition() {
		
		return null;
	}

	@Override
	public TuitionInfo getTuitionInfoById(int principalId) {
		TuitionInfo tuition = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_TUITION_BY_ID_SQL)) {
			pstmt.setInt(1, principalId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					tuition = TuitionInfo.builder()
							.collegeName(rs.getString("cname"))
							.semester(rs.getInt("semester"))
							.deptName(rs.getString("dname"))
							.studentId(rs.getInt("id"))
							.studentName(rs.getString("name"))
							.scholarshipAmount(rs.getInt("amount"))
							.tuitionAmount(rs.getInt("amount"))
							.scholarshipAmount(rs.getInt("amount"))
							.payment(rs.getInt("amount"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tuition;
	}

	@Override
	public void createTuition(Tuition tuition) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_TUITION)) {
				pstmt.setInt(1, tuition.getStudentId());
				pstmt.setInt(2, tuition.getTuiYear());
				pstmt.setInt(3, tuition.getSemester());
				pstmt.setInt(4, tuition.getTuiAmount());
				pstmt.setInt(5, tuition.getSchType());
				pstmt.setInt(6, tuition.getSchAmount());
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Tuition getTuitionByIdAndSemester(int studentId, int semester) {
		Tuition tuition = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_TUITION_BY_ID_SEMESTER_SQL)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					tuition = Tuition.builder()
							.studentId(rs.getInt("id"))
							.tuiYear(2024)
							.semester(semester)
							.tuiAmount(rs.getInt("amount"))
							.schType(1)
							.schAmount(rs.getInt("amount"))
							.status(0)							
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tuition;
	}

}
