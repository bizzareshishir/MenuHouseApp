package com.example.menuhouseapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.utils.Utils;

public class DashBoardActivity extends Activity {

	TextView txtUserName, txtHotelName;

	Context ctx;

	String hotelId, hotelName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_layout);
		init();
		hotelId = getIntent().getExtras().getString("hotel_id");
		hotelName = getIntent().getExtras().getString("hotel_name");
		txtUserName.setText("Welcome " + Utils.userName(ctx));
		txtHotelName.setText(hotelName);
	}

	private void init() {
		// TODO Auto-generated method stub
		txtUserName = (TextView) findViewById(R.id.txtUserName);
		txtHotelName = (TextView) findViewById(R.id.txtHotelName);

		ctx = this;
	}

	public void onClickLogout(View view) {
		Utils.showToast("Logout Successfully", ctx);
	}

	public void onClickViewMenu(View view) {

		Intent intent = new Intent(ctx, ViewMenuActivity.class);
		intent.putExtra("hotel_id", hotelId);
		intent.putExtra("hotel_name", hotelName);
		startActivity(intent);
	}
	
	public void onclickSendFeedback(View view){
		Intent intent = new Intent(ctx, FeedbackActivity.class);
		intent.putExtra("hotel_id", hotelId);
		intent.putExtra("hotel_name", hotelName);
		startActivity(intent);
	}
	
	public void onclickAboutUs(View view){
		Intent intent = new Intent(ctx, AboutUsActivity.class);
		intent.putExtra("hotel_id", hotelId);
		intent.putExtra("hotel_name", hotelName);
		startActivity(intent);
	}
	
	public void onClickRate(View view){
		Intent intent = new Intent(ctx, RateUsActivity.class);
		intent.putExtra("hotel_id", hotelId);
		intent.putExtra("hotel_name", hotelName);
		startActivity(intent);
	}

}
