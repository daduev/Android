package com.home.example.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {
	private MediaPlayer mPlayer;

	public void stop() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	public void play(Context c) {
		//	ѕредотвращает возможное создание нескольких экземпл€ров MediaPlayer
		stop();

		//	”держивайте ровно один экземпл€р MediaPlayer и только на то врем€, в котором он что-то воспроизводит
		mPlayer = MediaPlayer.create(c, R.raw.one_small_step);

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
