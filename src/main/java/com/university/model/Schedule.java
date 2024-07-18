package com.university.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Schedule {

	private int id;
	private int staffId;
	private Date startDay;
	private Date endDay;
	private String information;

}
