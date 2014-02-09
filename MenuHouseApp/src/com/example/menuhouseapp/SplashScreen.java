package com.example.menuhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// Welcome page of the user login or registration

				Intent i1 = new Intent(SplashScreen.this, LoginActivity.class);
				startActivity(i1);
				finish();
			}

		}, SPLASH_TIME_OUT);

	}

}
