package com.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class Utils {

	public static final String URL = "http://192.168.1.218:8081/RestaurantWebService/";
	public static final String LOGIN = "LoginServlet";
	public static final String REGISTRATION = "RegistrationServlet";

	public static final String CITY = "CityIdServlet";

	public static final String WARNING_1 = "Something went wrong";
	public static final String WARNING_2 = "All fields are mandatory";
	public static final String WARNING_3 = "Password mismatch";

	public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
	public static final String INCORRECT_USERNAME_PASSWORD = "Username or Password is incorrect";

	public static void showToast(String msg, Context ctx) {
		Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
	}

	public static ProgressDialog returnPDialog(Context ctx, String message) {

		ProgressDialog pdailg = new ProgressDialog(ctx);
		pdailg.setMessage(message);
		pdailg.setCancelable(false);

		return pdailg;
	}

	public static final String CITY_LIST = "1~Ahmednagar@2~Akola@3~Amravati@4~Aurangabad@5~Beed@6~Bhandara@7~Buldhana@8~Chandrapur@9~Dhule@10~Gadchiroli@11~Gondia@12~Hingoli@13~Jalgaon@14~Jalna@15~Kolhapur@16~Latur@17~Mumbai City@18~Mumbai Suburban@19~Nagpur@20~Nanded@21~Nandurbar@22~Nashik@23~Osmanabad@24~Parbhani@25~Pune@26~Raigad@27~Ratnagiri@28~Sangli@29~Satara@30~Sindhudurg@31~Solapur@32~Thane@33~Wardha@34~Washim@35~Yavatmal";
}
