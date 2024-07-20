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
public class Subject {
	private int id;
	private String name;
	private int professorId;
	private String roomId;
	private int deptId;
	private String type;
	private int subYear;
	private int semester;
	private String subDay;
	private int startTime;
	private int endTime;
	private int grades;
	private int capacity;
	private int numOfStudent;
}
