package com.university.model;

<<<<<<< HEAD
=======
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
public class Grade {
	
	private String grade;
	private double gradeValue;
>>>>>>> bc67c008988d49cf71499dcedd34d52a7821c5cc

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Grade {
	private String grade;
	private float gradeValue;
}
