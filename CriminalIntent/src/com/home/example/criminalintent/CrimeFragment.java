package com.home.example.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.home.example.criminalintent.consts.CommonLogger;

public class CrimeFragment extends Fragment {
	
	public static final String EXTRA_CRIME_ITEM_POS = "com.home.example.criminalintent.crime_item_pos";
	public static final String EXTRA_CRIME_ID = "com.home.example.criminalintent.crime_id";
	private static final String DIALOG_DATE = "date";
	private static final int REQUEST_DATE = 0;

	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	
	//������������ ��� �������� ���������� ���������, ���� ����� ���� �������� ���������, ���� ���������� �� ������� ������� � ����������� ��������
	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		return fragment;
	}	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//	���������� ������� �����, ���� �������� �������� ������ ����� �������� ���������� ���� onCreateOptionsMenu (� �������� �� ���������� � ��������)
		setHasOptionsMenu(true);

		//mCrime = new Crime();
		//������ ������ ������ �� ��������, ���� ����� �.�. �������� ������� �� ���������� (������),�������
		//UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
		
		//�� ������� ������, �� ����� ������ �.�. �������� �� ������� �� ����������
		UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		CommonLogger.INFO(this.getClass(), "onDestroy");
		getActivity().setResult(Activity.RESULT_FIRST_USER, null);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		
		//	�������� ������ ������� �� �������������
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			//	�������� �� ������������� ������������ ���������� ��������� ���������, ��� ������ �����
			if(NavUtils.getParentActivityName(getActivity()) != null) {
				//	����� ������ ��� ����������� ������ ����� � ��������� ����
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}		

		mTitleField = (EditText) v.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence c, int start, int before, int count) {
				mCrime.setTitle(c.toString());
			}

			public void beforeTextChanged(CharSequence c, int start, int count, int after) {
				// ����� ��������� ��������� ������ �����
			}

			public void afterTextChanged(Editable c) {
				// ����� ��������� ��������� ������ �����
			}
		});
		
		mDateButton = (Button) v.findViewById(R.id.crime_date);
		updateButtonDate();
		//mDateButton.setEnabled(false);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				FragmentManager fm = getActivity().getSupportFragmentManager();						
				//DatePickerFragment dialog = new DatePickerFragment();
				DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
				//	���������� ��������� ���������, ��� ������ �������
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
				
			}
		});
		
		mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				// ���������� ����� ��������� ������������
				mCrime.setSolved(isChecked);
			}
		});		

		return v;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//	�������� �� ������������� ������������ ���������� ��������� ���������, ��� ������ �����
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				//	������� � ������������ ����������
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}	
	
	//	����������� ������ �� DatePickerFragment
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) 
			return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setDate(date);
			updateButtonDate();
		}
	}	
	
	public void updateButtonDate() {
		mDateButton.setText(DateFormat.format("dd MMMM yyyy HH:mm, cccc", mCrime.getDate()));
	}	

}
