package com.example.test;

import java.io.Serializable;

public class SaveState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int digit;
	
	public SaveState() {}
	
	public SaveState(int digit) {
		this.digit = digit;
	}

	public int getDigit() {
		return digit;
	}

	public void setDigit(int digit) {
		this.digit = digit;
	}
	
}
