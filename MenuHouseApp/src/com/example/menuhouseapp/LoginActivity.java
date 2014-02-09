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
import android.widget.TextView;

import com.sharedpreference.SharedPreferenceHelper;
import com.utils.Utils;
import com.utils.WebServiceUtil;

public class LoginActivity extends Activity {
	TextView txtReg;
	EditText edt_user_name, edt_password;
	String rep = "";

	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		init();

	}

	public void init() {

		context = LoginActivity.this;
		edt_user_name = (EditText) findViewById(R.id.edt_user_name);
		edt_password = (EditText) findViewById(R.id.edt_pass);
	}

	public void onClickSignIn(View view) {

		if (userName().length() > 0 && userPassword().length() > 0)
			new AsynLogin()
					.execute(new String[] { userName(), userPassword() });
		else {
			Utils.showToast("Please fill all details", context);
		}

	}

	public void onClickSignUp(View view) {

		startActivity(new Intent(context, RegistrationActivity.class));
		finish();
	}

	String userName() {

		return edt_user_name.getText().toString();
	}

	String userPassword() {

		return edt_password.getText().toString();
	}

	class AsynLogin extends AsyncTask<String, Void, Void> {

		ProgressDialog pdialog;

		String userName = "";

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

			if (rep != null) {
				if (rep.contains("1")) {

					SharedPreferenceHelper.savePreferences(
							SharedPreferenceHelper.USERNAME, userName, context);
					SharedPreferenceHelper.savePreferences(
							SharedPreferenceHelper.PHONE, rep.split("~")[1],
							context);
					SharedPreferenceHelper.savePreferences(
							SharedPreferenceHelper.ADDRESS, rep.split("~")[2],
							context);

					startActivity(new Intent(context, MainActivity.class));
					finish();
				}
			} if (rep.contains("0")){
				Utils.showToast(Utils.INCORRECT_USERNAME_PASSWORD, context);
			}
			else
				Utils.showToast(Utils.WARNING_1, context);

			super.onPostExecute(result);
		}

		@Override
		protected Void doInBackground(String... params) {
			userName = params[0];
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("uname", params[0]));
			nameValuePair.add(new BasicNameValuePair("password", params[1]));

			Log.e("newlog", params[0] + "--" + params[1]);
			String url = Utils.URL + Utils.LOGIN;

			try {
				rep = WebServiceUtil.getPostResponce(nameValuePair, url);

				Log.e("newlog", rep + "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
