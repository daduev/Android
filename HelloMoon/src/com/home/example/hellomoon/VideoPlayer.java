package com.home.example.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

public class VideoPlayer {
	private MediaPlayer mPlayer;

	public void stop() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	public void play(Context c, SurfaceHolder sh) {
		//	������������� ��������� �������� ���������� ����������� MediaPlayer
		stop();

		//	����������� ����� ���� ��������� MediaPlayer � ������ �� �� �����, � ������� �� ���-�� �������������
		mPlayer = MediaPlayer.create(c, R.raw.test_video);
		Log.i("HelloMoon", "mPlayer " + mPlayer);
		mPlayer.setDisplay(sh);
		
		Log.i("HelloMoon", "mPlayer.getVideoWidth() " + mPlayer.getVideoWidth());
		Log.i("HelloMoon", "mPlayer.getVideoHeight() " + mPlayer.getVideoHeight());

		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				stop();
			}
		});

		mPlayer.start();
	}
	
	public void pause(){
		if(mPlayer != null){
			if(mPlayer.isPlaying()){
				mPlayer.pause();
			}else{
				mPlayer.start();
			}
		}
	}	
}
