package com.university.repository.interfaces;

import java.util.List;

import com.university.model.Professor;



public interface ProfessorRepository {
	int addProfessor(Professor professor);
	void deleteProfessor(int id);
	Professor getProfessorByprofessorname(String name);
	Professor getProfessorByprofessorIdAndName(int id, String name);
	List<Professor> getAllProfessors();
}
