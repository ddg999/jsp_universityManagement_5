package com.university.repository;

import com.university.model.Staff;
import com.university.repository.interfaces.StaffRepository;

public class StaffRepositoryImpl implements StaffRepository {
	
	private static final String SELECT_STAFF_EMAIL = " SELECT staff_tb.email FROM staff_tb ";

//	@Override
//	public String getStaffEmail(String email) {
//		Staff staff = null;
//		try (Connection conn = DBUtil.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_EMAIL)) {
//			pstmt.setString(1, email);
//			try (ResultSet rs = pstmt.executeQuery()) {
//				if (rs.next()) {
//					staff = Staff.builder().email(rs.getString("email")).build();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return staff.getEmail();
//	}

	@Override
	public Staff getStaffEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Staff getStaffInfo(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
