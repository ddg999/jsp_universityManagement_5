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
	private String overview;
	private String objective;
	private String textbook;
	private String program;
}
