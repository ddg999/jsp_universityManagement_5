package com.university.repository.interfaces;

import com.university.model.Staff;

public interface StaffRepository {
	
	Staff getStaffInfo(String email);

	Staff getStaffEmail(String email);

}
