package com.home.example.criminalintent;

import java.util.ArrayList;

import com.home.example.criminalintent.consts.CommonLogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {
	
	private static final int REQUEST_CRIME = 1;
	private ArrayList<Crime> mCrimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
			dateTextView.setText(c.getDate().toString());
			
			CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			
			
			return convertView;
		}
		
	}

}
