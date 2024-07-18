package com.university.model;

import java.sql.Date;
<<<<<<< Updated upstream

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
public class Student {

	private int id;
	private String name;
	private Date birthDate;
	private String gender;
	private String address;
	private String tel;
	private String email;
	private int deptId;
	private int grade;
	private int semester;
	private Date entranceDate;
	private Date graduationDate;
	
=======

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

public class Student {
	private int id; // 학번
	private String name; // 이름
	private Date birthDate; // 생일
	private String gender; // 성별 (남성, 여성)
	private String address; // 주소
	private String tel; // 전화번호
	private String email; // 이메일
	private int deptId; // 학과아이디
	private int grade; // 학년
	private int semester; // 학기
	private Date entranceDate; // 입학날짜
	private Date graduationDate; // 졸업 날짜
>>>>>>> Stashed changes
}
