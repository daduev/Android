package com.home.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
	
	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	
	private ArrayList<Crime> crimes;
	
	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		crimes = new ArrayList<Crime>();
		
		/*
		for (int i = 0; i < 100; i++) {
			Crime c = new Crime();
			c.setTitle("Crime #" + i);
			c.setSolved(i % 2 == 0); // Для каждого второго объекта
			crimes.add(c);
		}
		*/		
	}
	
	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}
		return sCrimeLab;
	}

	public ArrayList<Crime> getCrimes() {
		return crimes;
	}	
	
	public Crime getCrime(UUID id){
		for(Crime c : crimes){
			if(c.getId().equals(id)){
				return c;
			}
		}
		return null;
	}
	
	public void addCrime(Crime c) {
		crimes.add(c);
	}	

}
