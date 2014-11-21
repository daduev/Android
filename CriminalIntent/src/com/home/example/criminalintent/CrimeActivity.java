package com.home.example.criminalintent;

import java.util.UUID;

import com.home.example.criminalintent.consts.CommonLogger;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		CommonLogger.INFO(this.getClass(), "createFragment()");
		
		//”ниверсальный метод передачи данных из одного фрагмента в другой (из активности в активность)
		//”ниверсальный т.к. отв€зывает зависимость фрагмента от активности, что есть хорошо, т.к. лучше когда активность знает об фрагменте который использует
		UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		
		return CrimeFragment.newInstance(crimeId);
	}
	
	
}
