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
public class TuitionInfo {
	
	private int semester;
	private String collegeName;
	private String deptName;
	private int studentId;
	private String studentName;
	private int scholarshipId;
	private int tuitionAmount;
	private int scholarshipAmount;
	private int payment;
	private String status;
	
}
