package com.example.test;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends ActionBarActivity {

	private static final String TAG = "CheatActivity";
	public static final String EXTRA_ANSWER_IS_TRUE = "com.example.test.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.example.test.answer_shown";

	private boolean mAnswerIsTrue;
	
	private TextView mAnswerTextView;
	private TextView mApiVersionTextView;
	private Button mShowAnswer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		Log.i(TAG, "Start CheatActivity");

		// Чтение дополнительных(информации) данных
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShownResult(true);
			}
		});
		
		mApiVersionTextView = (TextView) findViewById(R.id.apiVersionTextView);
		
		mApiVersionTextView.setText("API Version " + Build.VERSION.SDK_INT);
		
		setAnswerShownResult(false);
	}
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}

}
