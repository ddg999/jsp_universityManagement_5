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
public class StuSch {

	private int studentId;
	private int schYear;
	private int semester;
	private int schType;
	
}
