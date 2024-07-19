package com.university.repository.interfaces;

import java.util.List;

import com.university.model.CollTuit;
import com.university.model.College;
import com.university.model.Department;
import com.university.model.Room;
import com.university.model.Subject;

public interface AdminRepository {
	void addCollege(College college);

	List<College> getAllColleges();

	void updateCollege(College college, int collegeId);

	void deleteCollege(String collegeName);

	void addDepartment(Department department);

	List<Department> getAllDepartments();

	void updateDepartment(Department department, int departmentId);

	void deleteDepartment(int departmentId);

	void addRoom(Room room);

	List<Room> getAllRooms();

	void updateRoom(Room room, int roomId);

	void deleteRoom(String roomId);

	void addSubject(Subject subject);

	List<Subject> getAllSubjects();

	void updateSubject(Subject subject, int subjectId);

	void deleteSubject(int subjectId);

	void addCollTuit(CollTuit collTuit);

	List<CollTuit> getAllCollTuits();

	void updateCollTuit(CollTuit collTuit, int collegeId);

	void deleteCollTuit(int collegeId);
}
