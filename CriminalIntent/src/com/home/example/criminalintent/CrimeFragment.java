package com.home.example.criminalintent;

import java.util.UUID;

import com.home.example.criminalintent.consts.CommonLogger;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	
	public static final String EXTRA_CRIME_ITEM_POS = "com.home.example.criminalintent.crime_item_pos";
	public static final String EXTRA_CRIME_ID = "com.home.example.criminalintent.crime_id";

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

		//mCrime = new Crime();
		//������ ������ ������ �� ��������, ���� ����� �.�. �������� ������� �� ���������� (������),�������
		//UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
		CommonLogger.DEBUG(this.getClass(), getActivity().getIntent().toString());
		
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);

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
		mDateButton.setText(DateFormat.format("dd MMMM yyyy HH:mm, cccc", mCrime.getDate()));
		mDateButton.setEnabled(false);
		
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

}
