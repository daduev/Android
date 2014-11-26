package com.home.example.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.home.example.criminalintent.consts.CommonLogger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment {
	
	public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";
			
	private Date mDate;
	
	public static DatePickerFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		CommonLogger.DEBUG(this.getClass(), "------------------------onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		CommonLogger.DEBUG(this.getClass(), "------------------------onCreateDialog");
		
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		// создание объекта Calendar дл€ получени€ года, мес€ца и дн€
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		
		DatePicker datePicker = (DatePicker)view.findViewById(R.id.dialog_dateDatePicker);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				// ѕреобразование года, мес€ца и дн€ в объект Date
				mDate = new GregorianCalendar(year, month, day).getTime();
				// обновление аргумента дл€ сохранени€
				// выбранного значени€ при повороте
				// альтернатива сохранени€ значени€ в методе onSaveInstanceState
				getArguments().putSerializable(EXTRA_DATE, mDate);
			}
		});		
		
		return new AlertDialog.Builder(getActivity()).setView(view).setTitle(R.string.date_picker_title)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).create();
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		//	ƒергаем метод в ручную, т.к. у фрагмента этот метод не вызываетс€ в автомате, а активити он вызываетс€ в автомате
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}	
	
	

}
