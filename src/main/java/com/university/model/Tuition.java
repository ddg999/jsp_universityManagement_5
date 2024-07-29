package com.university.model;

import com.university.controller.TuitionController;

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
public class Tuition {

	private int studentId;
	private int tuiYear;
	private int semester;
	private int tuiAmount;
	private int schType;
	private int schAmount;
	private int status;

	public String tuiFormat() {
		return TuitionController.numberFormat(tuiAmount);
	}

	public String schFormat() {
		return TuitionController.numberFormat(schAmount);
	}

	public String paymentFormat() {
		Integer payAmount = tuiAmount - schAmount;
		return TuitionController.numberFormat(payAmount);
	}
}