package com.university.repository.interfaces;

import java.util.List;

import com.university.model.CollTuit;
import com.university.model.College;
import com.university.model.Department;
import com.university.model.Room;
import com.university.model.Subject;

public interface AdminRepository {
	
	// 단과대 CRUD
	void addCollege(College college);

	List<College> getAllColleges();

	void updateCollege(College college, int collegeId);

	void deleteCollege(String collegeName);

	// 학과 CRUD
	void addDepartment(Department department);

	List<Department> getAllDepartments();

	void updateDepartment(Department department, int departmentId);

	void deleteDepartment(int departmentId);

	// 강의실 CRUD
	void addRoom(Room room);

	List<Room> getAllRooms();

	void updateRoom(Room room, int roomId);

	void deleteRoom(String roomId);

	// 강의 CRUD
	void addSubject(Subject subject);

	List<Subject> getAllSubjects();

	void updateSubject(Subject subject, int subjectId);

	void deleteSubject(int subjectId);

	// 단대별 등록금 CRUD
	void addCollTuit(CollTuit collTuit);

	List<CollTuit> getAllCollTuits();

	void updateCollTuit(CollTuit collTuit, int collegeId);

	void deleteCollTuit(int collegeId);
}
