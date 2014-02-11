package com.example.menuhouseapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.data.AboutUsData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sharedpreference.SharedPreferenceHelper;
import com.utils.Utils;
import com.utils.WebServiceUtil;

public class AboutUsActivity extends Activity {

	TextView textUserName, txtHotelName;
	Context ctx;

	String hotelId;
	AboutUsData aboutUsObj = new AboutUsData();

	TextView contactNo, name, address, city, pin, email, descrption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us_activity);

		init();

		hotelId = getIntent().getExtras().getString("hotel_id");
		txtHotelName.setText(getIntent().getExtras().getString("hotel_name"));
		textUserName.setText("Welcome "
				+ SharedPreferenceHelper.getPreference(ctx,
						SharedPreferenceHelper.USERNAME));

		new AsyncAboutUsHotel(new String[] { hotelId }).execute();

	}

	private void init() {
		// TODO Auto-generated method stub
		ctx = this;

		textUserName = (TextView) findViewById(R.id.txtUserName);
		txtHotelName = (TextView) findViewById(R.id.txtHotelName);
		contactNo = (TextView) findViewById(R.id.contactNo);
		name = (TextView) findViewById(R.id.name);
		address = (TextView) findViewById(R.id.address);
		city = (TextView) findViewById(R.id.city);
		pin = (TextView) findViewById(R.id.pin);
		email = (TextView) findViewById(R.id.email);
		descrption = (TextView) findViewById(R.id.descrption);
	}

	public void onClickLogout(View view) {
		Utils.showToast("Logout Successfully", ctx);
	}

	class AsyncAboutUsHotel extends AsyncTask<Void, Void, Void> {

		String hotelId = "";
		String response = "";

		public AsyncAboutUsHotel(String[] params) {
			// TODO Auto-generated constructor stub
			this.hotelId = params[0];

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

			String url = Utils.URL + Utils.ABOUT_US;
			try {
				response = WebServiceUtil.getResponse(url+"?hotel_id="+hotelId);
				Log.e("newlog", response+"<--");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			if (response != null) {
				try {

					String tokens[] = response.split("~");

					aboutUsObj.contactNo = tokens[0];
					aboutUsObj.name = tokens[1];
					aboutUsObj.address = tokens[2];
					aboutUsObj.city = tokens[3];
					aboutUsObj.pin = tokens[4];
					aboutUsObj.email = tokens[5];
					aboutUsObj.descrption = tokens[6];

					setTextDetail(aboutUsObj);

				} catch (Exception e) {
					// TODO: handle exception
					Utils.showToast(Utils.WARNING_1, ctx);
				}

			} else
				Utils.showToast(Utils.WARNING_1, ctx);
			super.onPostExecute(result);
		}

		void setTextDetail(AboutUsData abtUs) {
			contactNo.setText(abtUs.contactNo);
			name.setText(abtUs.name);
			address.setText(abtUs.address);
			city.setText(abtUs.city);
			pin.setText(abtUs.pin);
			email.setText(abtUs.email);
			descrption.setText(abtUs.descrption);
		}
	}

}
