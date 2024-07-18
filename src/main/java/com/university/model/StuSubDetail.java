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
public class StuSubDetail {

	private int id;
	private int studentId;
	private int subjectId;
	private int absent;
	private int lateness;
	private int homework;
	private int midExam;
	private int finalExam;
	private int convertedMark;
	
}
