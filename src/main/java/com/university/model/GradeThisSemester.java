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
public class GradeThisSemester {
	private int subYear;
	private int semester;
	private int subjectId;
	private String subjectName;
	private String professorName;
	private String type;
	private String grade;
	private int grades;
	private int rank;
	private int evaluationId;
}
