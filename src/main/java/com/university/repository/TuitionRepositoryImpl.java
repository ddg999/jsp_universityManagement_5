package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.university.model.AvgGrade;
import com.university.model.StuSch;
import com.university.model.Tuition;
import com.university.model.TuitionInfo;
import com.university.repository.interfaces.TuitionRepository;
import com.university.util.DBUtil;

public class TuitionRepositoryImpl implements TuitionRepository{

	private static final String INSERT_TUITION = " INSERT INTO tuition_tb (student_id, tui_year, semester, tui_amount, sch_type, sch_amount) VALUES (?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_TUITION_BY_ID_SQL = " SELECT s.semester, c.name AS cname, d.name AS dname, s.id, s.name, ctt.amount, sst.sch_type, st.max_amount, (ctt.amount-st.max_amount) as payment FROM college_tb AS c JOIN department_tb AS d ON c.id = d.college_id JOIN student_tb AS s ON d.id = s.dept_id JOIN coll_tuit_tb AS ctt ON c.id = ctt.college_id join stu_sch_tb as sst on sst.student_id = s.id  join  scholarship_tb as st on st.type = sst.sch_type WHERE s.id = ?; ";
	private static final String SELECT_TUITION_BY_ID_SEMESTER_SQL = " SELECT s.id, ct.amount, sst.sch_type, st.max_amount FROM student_tb AS s JOIN department_tb AS d ON s.dept_id = d.id JOIN college_tb AS c ON c.id = d.college_id JOIN coll_tuit_tb AS ct ON c.id = ct.college_id join stu_sch_tb as sst on sst.student_id = s.id join scholarship_tb as st on st.type = sst.sch_type WHERE s.id = ? AND s.semester = ? ";
	
	private static final String SELECT_TUITION_SQL_BY_ID = " select * from tuition_tb where student_id = ? ";
	
	// 학생아이디를 통해 학생 아이디, 평균 점수 구하는 쿼리	
	private static final String SELECT_AVGGRADE_BY_ID = " SELECT s.student_id, AVG(g.grade_value) AS avgGrade FROM stu_sub_tb AS s JOIN grade_tb AS g ON s.grade = g.grade GROUP BY s.student_id; ";
	
	// 장학금 유형별 인설트 쿼리
	private static final String INSERT_STU_SCH = " insert into stu_sch_tb (student_id, sch_year, semester, sch_type) values (?,?,?,?) ";
	
	private static final String UPDATE_STATUS_SQL = " update tuition_tb set status = 1 where student_id = ? ";
	
	@Override
	public List<StuSch> createStuSch(int studentId, int schType) {
		List<StuSch> stuSchList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_STU_SCH)){
				pstmt.setInt(1, studentId);
	            pstmt.setInt(2, 2024);
	            pstmt.setInt(3, 1);
	            pstmt.setInt(4, schType);
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return stuSchList;
	}
	
	@Override
	public List<AvgGrade> getAvgGrade() {
		List<AvgGrade> avgGradeList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_AVGGRADE_BY_ID)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					avgGradeList.add(AvgGrade.builder()
							.studentId(rs.getInt("student_id"))
							.avgGrade(rs.getDouble("avgGrade"))
							.build());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avgGradeList;
	}
	
	@Override
	public List<Tuition> selectByStudentIdAndStatus(int studentId) {
		List<Tuition> tuitionList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_TUITION_SQL_BY_ID)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					tuitionList.add(Tuition.builder()
							.studentId(rs.getInt("student_id"))
							.tuiYear(rs.getInt("tui_year"))
							.semester(rs.getInt("semester"))
							.tuiAmount(rs.getInt("tui_amount"))
							.schType(rs.getInt("sch_type"))
							.schAmount(rs.getInt("sch_amount"))
							.status(rs.getInt("status"))							
							.build());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tuitionList;
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
							.tuitionAmount(rs.getInt("amount"))
							.scholarshipAmount(rs.getInt("max_amount"))
							.scholarshipId(rs.getInt("sch_type"))
							.payment(rs.getInt("payment"))
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
	public int createTuition(Tuition tuition) {
		int rowCount = 0;
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_TUITION)) {
				pstmt.setInt(1, tuition.getStudentId());
				pstmt.setInt(2, tuition.getTuiYear());
				pstmt.setInt(3, tuition.getSemester());
				pstmt.setInt(4, tuition.getTuiAmount());
				pstmt.setInt(5, tuition.getSchType());
				pstmt.setInt(6, tuition.getSchAmount());
				rowCount = pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
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
							.schType(rs.getInt("sch_type"))
							.schAmount(rs.getInt("max_amount"))
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

	@Override
	public void updateStatus(int studentId) {
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_STATUS_SQL)){
				pstmt.setInt(1, studentId);
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public Tuition getTuitionBYId(int studentId) {
		Tuition tuition = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_TUITION_SQL_BY_ID)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					tuition = Tuition.builder()
							.studentId(rs.getInt("student_id"))
							.tuiYear(rs.getInt("tui_year"))
							.semester(rs.getInt("semester"))
							.tuiAmount(rs.getInt("tui_amount"))
							.schType(rs.getInt("sch_type"))
							.schAmount(rs.getInt("sch_amount"))
							.status(rs.getInt("status"))							
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
