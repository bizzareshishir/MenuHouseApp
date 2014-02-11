package com.example.menuhouseapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sharedpreference.SharedPreferenceHelper;
import com.utils.Utils;
import com.utils.WebServiceUtil;

public class FeedbackActivity extends Activity {

	TextView textUserName,txtHotelName;
	EditText edtFeedback;
	Context ctx;

	String hotelId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_activity);

		init();

		hotelId = getIntent().getExtras().getString("hotel_id");
		txtHotelName.setText(getIntent().getExtras().getString("hotel_name"));
		textUserName.setText("Welcome "
				+ SharedPreferenceHelper.getPreference(ctx,
						SharedPreferenceHelper.USERNAME));

	}

	private void init() {
		// TODO Auto-generated method stub
		ctx = this;

		txtHotelName=(TextView)findViewById(R.id.txtHotelName);
		textUserName = (TextView) findViewById(R.id.txtUserName);
		edtFeedback = (EditText) findViewById(R.id.edtFeedback);
	}

	public void onClickLogout(View view) {
		Utils.showToast("Logout Successfully", ctx);
	}

	public void onClickSendFeedback(View view) {

		new AsyncSendFeedbakHotel(new String[] {
				hotelId,
				SharedPreferenceHelper.getPreference(ctx,
						SharedPreferenceHelper.USERNAME),
				edtFeedback.getText().toString() }).execute();
	}

	class AsyncSendFeedbakHotel extends AsyncTask<Void, Void, Void> {

		String hotelId="",userName="",feedback = "";
		String response = "";

		public AsyncSendFeedbakHotel(String[] params) {
			// TODO Auto-generated constructor stub
			this.hotelId = params[0];
			this.userName = params[1];
			this.feedback = params[2];
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("hotel_id", hotelId));
			nameValuePair.add(new BasicNameValuePair("user_name", userName));
			nameValuePair.add(new BasicNameValuePair("feedback", feedback));
			
			String url = Utils.URL + Utils.FEEDBACK;
			try {
				response = WebServiceUtil.getPostResponce(nameValuePair, url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			if(response!=null && response.contains("1")){
				Utils.showToast("Feedback sent successfully", ctx);
				finish();
			}else
				Utils.showToast(Utils.WARNING_1, ctx);
			super.onPostExecute(result);
		}

	}

}
