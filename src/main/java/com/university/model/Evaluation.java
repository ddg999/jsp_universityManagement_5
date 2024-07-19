package com.university.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Evaluation {
	private int evaluationId;
	private int studentId;
	private int subjectId;
	private int answer1;
	private int answer2;
	private int answer3;
	private int answer4;
	private int answer5;
	private int answer6;
	private int answer7;
	private String improvements;
	
	public String answerSum() {
		 double answerSum = (double)(answer1 + answer2 + answer3 + answer4 + answer5 + answer6 + answer7) / 7;
		 String result = String.format("%.2f", answerSum);
	 return result;
	}
	
}
