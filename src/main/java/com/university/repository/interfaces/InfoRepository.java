package com.university.repository.interfaces;

import com.university.model.Staff;

public interface InfoRepository {
	
	int addUser(Staff staff);
	
	int updateStaff(Staff staff, int principalId);
	
}
