package com.home.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import com.home.example.criminalintent.consts.CommonLogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class CrimePagerActivity extends FragmentActivity {
	
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		CommonLogger.INFO(this.getClass(), "onCreate");
		
		super.onCreate(savedInstanceState);
		
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mCrimes = CrimeLab.get(this).getCrimes();
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return mCrimes.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				CommonLogger.INFO(this.getClass(), "CrimeFragment.newInstance");
				Crime crime = mCrimes.get(pos);
				return CrimeFragment.newInstance(crime.getId());
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			//находится ли анимация страницы в процессе активного перетаскивания, перехода в устойчивое состояние или в простое
			public void onPageScrollStateChanged(int state) {}
			
			//где будет находиться	страница
			public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {}
			
			//какая страница является текущей
			public void onPageSelected(int pos) {
				Crime crime = mCrimes.get(pos);
				if (crime.getTitle() != null) {
					setTitle(crime.getTitle());
				}
			}
		});

		
		//----------------------------------------------------------------------------------------
		//Метод получния позиции объекта в листе, описан в книге
		/*
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < mCrimes.size(); i++) {
			if (mCrimes.get(i).getId().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}	
		*/	
		
		//Метод получния позиции объекта в листе, собственная реализация		
		int crimeIndex = getIntent().getIntExtra(CrimeFragment.EXTRA_CRIME_ITEM_POS, 0);
		mViewPager.setCurrentItem(crimeIndex);
		//----------------------------------------------------------------------------------------		
	}	

}
