package com.university.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.university.model.Staff;
import com.university.repository.interfaces.StaffRepository;
import com.university.util.DBUtil;

public class StaffRepositoryImpl implements StaffRepository {
	
	private static final String SELECT_STAFF_EMAIL = " SELECT staff_tb.email FROM staff_tb ";

	@Override
	public String getStaffEmail(String email) {
		Staff staff = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_EMAIL)) {
			pstmt.setString(1, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					staff = Staff.builder().email(rs.getString("email")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staff.getEmail();
	}


}
