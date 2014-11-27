package com.home.example.hellomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonVideoFragment extends Fragment {
	
	private VideoPlayer mPlayer = new VideoPlayer();
	private Button mPlayButton;
	private Button mStopButton;
	private Button mPauseButton;
	private SurfaceView surfaceView; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_hello_moon_video, container, false);
		
		surfaceView = (SurfaceView) view.findViewById(R.id.hellomoon_videoSurfaceView);
		
		mPlayButton = (Button)view.findViewById(R.id.hellomoon_playButton);
		mPlayButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.i("HelloMoon", "surfaceView " + surfaceView);
				Log.i("HelloMoon", "surfaceView.getHolder() " + surfaceView.getHolder());
				mPlayer.play(getActivity(), surfaceView.getHolder());
			}
		});	
		
		mStopButton = (Button)view.findViewById(R.id.hellomoon_stopButton);
		mStopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mPlayer.stop();
			}
		});
		
		mPauseButton = (Button)view.findViewById(R.id.hellomoon_pauseButton);
		mPauseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mPlayer.pause();
			}
		});		
		
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mPlayer.stop();
	}	

}
