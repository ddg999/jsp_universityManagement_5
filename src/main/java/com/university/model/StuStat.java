package com.university.model;

import java.sql.Date;

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
public class StuStat {

	private int id;
	private int studentId;
	private String status;
	private Date fromDate;
	private Date toDate;
	private int breakAppId;
	
}
