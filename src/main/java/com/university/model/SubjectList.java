package com.university.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class SubjectList {

	private int year;
	private int semester;
	private String university;
	private String department;
	private int subjectNum;
	private String type;
	private String subject;
	private String teacher;
	private int grade;
	private int stu_Num;
	private int capacity;
}
