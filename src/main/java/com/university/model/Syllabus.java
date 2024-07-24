package com.university.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Syllabus {
	private int subjectId;
	private String name;
	private int subYear;
	private int semester;
	private int grades;
	private String type;
	private String subDay;
	private int startTime;
	private int endTime;
	private String roomId;
	private String collName;
	private String professorName;
	private String deptName;
	private String tel;
	private String email;
	private String overview;
	private String objective;
	private String textbook;
	private String program;
}
