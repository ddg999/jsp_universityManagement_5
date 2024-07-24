package com.university.model;

import java.util.List;

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

	public boolean subjectBoolean(Subject subject, List<Subject> subjectList) {
		for (int i = 0; i < subjectList.size(); i++) {
			if ((subjectList.get(i).getStartTime() <= subject.getStartTime()
					&& subject.getStartTime() < subjectList.get(i).getEndTime())
					|| (subjectList.get(i).getStartTime() < subject.getEndTime()
							&& subject.getEndTime() <= subjectList.get(i).getEndTime())) {
				return false;
			}
		}
		return true;

	}

}
