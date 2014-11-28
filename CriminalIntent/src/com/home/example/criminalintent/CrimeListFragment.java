package com.home.example.criminalintent;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.home.example.criminalintent.consts.CommonLogger;

public class CrimeListFragment extends ListFragment {
	
	private static final int REQUEST_CRIME = 1;
	private ArrayList<Crime> mCrimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//	Необходимо вызвать метод, чтоб фрагмент менеджер вызвал метод создания командного меню onCreateOptionsMenu (в активити он вызывается в автомате)
		setHasOptionsMenu(true);
		
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);
		
		CommonLogger.INFO(this.getClass(), c.getTitle() + " was clicked");
		
		// Запуск CrimeActivity
		//Intent i = new Intent(getActivity(), CrimeActivity.class);
		//i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		//startActivity(i);

		
		// Запуск CrimePagerActivity с объектом Сrime
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		i.putExtra(CrimeFragment.EXTRA_CRIME_ITEM_POS, position);
		//startActivity(i);
		
		startActivityForResult(i, REQUEST_CRIME);		
	}	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CRIME) {
			CommonLogger.INFO(this.getClass(), "onActivityResult resultCode=" + requestCode);
		}
	}	
	
	@Override
	public void onResume() {
		super.onResume();
		//Обновление списка после возрата
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}	
	
	//	Метод создания командного меню, в активности вызывается в автомате, в фрагменте при вызове метода setHasOptionsMenu(true), в onCreate
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
	}
	
	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_new_crime:
				Crime crime = new Crime();
				CrimeLab.get(getActivity()).addCrime(crime);
				Intent i = new Intent(getActivity(), CrimePagerActivity.class);
				i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
				startActivityForResult(i, 0);
				return true;
			case R.id.menu_item_show_subtitle:
				if(getActivity().getActionBar().getSubtitle() == null) {
					getActivity().getActionBar().setSubtitle(R.string.subtitle);
					item.setTitle(R.string.hide_subtitle);
				}else{
					getActivity().getActionBar().setSubtitle(null);
					item.setTitle(R.string.show_subtitle);
				}
				return true;				
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		
		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Если мы не получили представление, заполняем его (см.выше super(getActivity(), 0, crimes) представление 0)
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			}
			
			// Настройка представления для объекта Crime
			Crime c = getItem(position);
			
			TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			
			TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(DateFormat.format("dd MMMM yyyy HH:mm, cccc", c.getDate()));
			
			CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			
			
			return convertView;
		}
		
		
		
	}

}
