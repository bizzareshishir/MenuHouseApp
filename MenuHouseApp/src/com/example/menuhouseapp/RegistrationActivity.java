package com.example.menuhouseapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.adapters.CityAdapter;
import com.data.CityInfo;
import com.sharedpreference.SharedPreferenceHelper;
import com.utils.Utils;
import com.utils.WebServiceUtil;

public class RegistrationActivity extends Activity {

	EditText edt_name, edt_address, edt_pincode, edt_phone_number,
			edt_username, edt_pass, edt_confirm_pass;

	Context context;
	String rep = "";

	Spinner spinner_city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		init();

		downloadCity();
	}

	private void downloadCity() {
		// TODO Auto-generated method stub
		String toks[] = Utils.CITY_LIST.split("@");

		CityInfo rArray[] = new CityInfo[toks.length];

		for (int i = 0; i < toks.length; i++) {

			String[] tokChild = toks[i].split("~");
			String city_id = tokChild[0];
			String city_name = tokChild[1];
			CityInfo city = new CityInfo();
			city.id = city_id;
			city.cityName = city_name;
			rArray[i] = city;
		}
		CityAdapter cityModel = new CityAdapter(getApplicationContext(),
				android.R.layout.simple_spinner_dropdown_item, rArray);
		
		spinner_city.setAdapter(cityModel);
	}

	private void init() {
		// TODO Auto-generated method stub

		context = this;
		edt_name = (EditText) findViewById(R.id.edt_name);
		edt_address = (EditText) findViewById(R.id.edt_address);
		edt_pincode = (EditText) findViewById(R.id.edt_pincode);
		edt_phone_number = (EditText) findViewById(R.id.edt_phone_number);
		edt_username = (EditText) findViewById(R.id.edt_username);
		edt_pass = (EditText) findViewById(R.id.edt_pass);
		edt_confirm_pass = (EditText) findViewById(R.id.edt_confirm_pass);

		spinner_city = (Spinner) findViewById(R.id.spinner_city);

		if (true) {
			edt_name.setText("shishir");
			edt_address.setText("thane");
			edt_pincode.setText("400601");
			edt_phone_number.setText("9820494835");
			edt_username.setText("shishir");
			edt_pass.setText("shishir");
			edt_confirm_pass.setText("shishir");
		}
	}

	public void onClickSignUp(View view) {

		if (check()) {

			CityInfo city =(CityInfo)spinner_city.getSelectedItem();
			if (getEdt_pass().equals(getEdt_confirm_pass())) {
				String params[] = { getEdt_name(), getEdt_address(),
						getEdt_pincode(), getEdt_phone_number(),
						getEdt_username(), getEdt_pass() ,city.id};
				new AsynRegistration().execute(params);
			} else {
				Utils.showToast(Utils.WARNING_3, context);
			}

		} else {
			Utils.showToast(Utils.WARNING_2, context);
		}
	}

	boolean check() {

		if (getEdt_name().length() > 0 && getEdt_address().length() > 0
				&& getEdt_pincode().length() > 0
				&& getEdt_phone_number().length() > 0
				&& getEdt_username().length() > 0 && getEdt_pass().length() > 0
				&& getEdt_confirm_pass().length() > 0)
			return true;
		else
			return false;
	}

	class AsynRegistration extends AsyncTask<String, Void, Void> {

		ProgressDialog pdialog;
		String username = "";
		String phone = "";
		String address = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pdialog = Utils.returnPDialog(context, "Loading...Please wait");
			pdialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			pdialog.dismiss();

			if (rep != null && rep.contains("1")) {

				SharedPreferenceHelper.savePreferences(
						SharedPreferenceHelper.USERNAME, username, context);
				SharedPreferenceHelper.savePreferences(
						SharedPreferenceHelper.PHONE, phone, context);
				SharedPreferenceHelper.savePreferences(
						SharedPreferenceHelper.ADDRESS, address, context);

				startActivity(new Intent(context, CityListActivity.class));
				finish();
			}
			if (rep != null && rep.contains("0")) {
				Utils.showToast(Utils.USERNAME_ALREADY_EXISTS, context);
			} else
				Utils.showToast(Utils.WARNING_1, context);

			super.onPostExecute(result);
		}

		@Override
		protected Void doInBackground(String... params) {
			username = params[4];
			phone = params[3];
			address = params[1];
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("name", params[0]));
			nameValuePair.add(new BasicNameValuePair("address", params[1]));
			nameValuePair.add(new BasicNameValuePair("pincode", params[2]));
			nameValuePair.add(new BasicNameValuePair("phonenumber", params[3]));
			nameValuePair.add(new BasicNameValuePair("uname", params[4]));
			nameValuePair.add(new BasicNameValuePair("password", params[5]));
			nameValuePair.add(new BasicNameValuePair("city_id", params[6]));

			String url = Utils.URL + Utils.REGISTRATION;

			try {
				rep = WebServiceUtil.getPostResponce(nameValuePair, url);

				Log.e("newlog", rep + "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public String getEdt_name() {
		return edt_name.getText().toString().trim();
	}

	public String getEdt_address() {
		return edt_address.getText().toString().trim();
	}

	public String getEdt_pincode() {
		return edt_pincode.getText().toString().trim();
	}

	public String getEdt_phone_number() {
		return edt_phone_number.getText().toString().trim();
	}

	public String getEdt_username() {
		return edt_username.getText().toString().trim();
	}

	public String getEdt_pass() {
		return edt_pass.getText().toString().trim();
	}

	public String getEdt_confirm_pass() {
		return edt_confirm_pass.getText().toString().trim();
	}
}
