package com.home.example.criminalintent.consts;

import android.util.Log;

public final class CommonLogger {
	
	private final static String TAG = "CommonLogger";
	
	public static <T> void INFO(Class<T> clazz, String msg){
		Log.i(TAG, "Class: " + clazz.getSimpleName() + " msg: " + msg);
	}
	
	public static <T> void DEBUG(Class<T> clazz, String msg){
		Log.d(TAG, "Class: " + clazz.getSimpleName() + " msg: " + msg);
	}	

}
