package com.home.example.criminalintent;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Crime implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String title;
	private Date date;
	private boolean solved;
	
	public Crime() {
		this.id = UUID.randomUUID();
		this.date = new Date();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UUID getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}
	
	@Override
	public String toString() {
		return title;
	}	

}
