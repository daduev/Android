package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";
	private static final String KEY_INDEX = "index";
	private MainActivity INSTANCE = this;
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mCheatButton;
	private TextView mQuestionTextView;
	private boolean mIsCheater;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] 
	{
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true)
	};
	
	private int mCurrentIndex = 0;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "onCreate(Bundle) called");
		
		setContentView(R.layout.activity_main);
		
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		
		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
		
		
		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});	
		
		mNextButton = (Button) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
			}
		});
		
		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				//Вызов другой активности
				Intent intent = new Intent(MainActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				//Передача информации другой активности(компонент)
				intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);

				//startActivity(intent);
				//Получения информации от потомков (дочерняя активность), которую вызывает текущий родитель (0-код идентификатор дочерний активности)
				startActivityForResult(intent, 0);
			}
		});
		
		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
			Log.i(TAG, "----------------------savedInstanceState " + savedInstanceState.getInt(KEY_INDEX));
		}
		
		mIsCheater = false;
		updateQuestion();
	}
	
	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageResId = 0;
		
		if (mIsCheater) {
			messageResId = R.string.judgment_toast;
		} else {
			if(userPressedTrue == answerIsTrue) {
				messageResId = R.string.correct_toast;
			}else{
				messageResId = R.string.incorrect_toast;
			}
		}
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Вызывается когда активность останавливается, происходит сохранения информации, для получнения ее в методе onCreate
	//При повороте устройства, вызывается это методот, и метод onCreate
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "requestCode= " + requestCode);
		Log.i(TAG, "resultCode= " + resultCode);
		if (data == null) {
			return;
		}
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() called");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}	
}
