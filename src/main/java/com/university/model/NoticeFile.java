package com.university.model;

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
public class NoticeFile {
	private int noticeId;
	private String originFileName;
	private String uuidFileName;
}
