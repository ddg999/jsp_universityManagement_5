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
public class Professor {

	private int id;
	private String name;
	private Date birthDate;
	private String address;
	private String tel;
	private String email;
	private int deptId;
	private Date hireDate;
	
}
