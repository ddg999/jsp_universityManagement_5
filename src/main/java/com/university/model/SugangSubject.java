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
public class SugangSubject {
	private String collName;
	private String deptName;
	private int id;
	private String type;
	private String name;
	private String professorName;
	private int grades;
	private String subDay;
	private int startTime;
	private int endTime;
	private String roomId;
	private int numOfStudent;
	private int capacity;
}
