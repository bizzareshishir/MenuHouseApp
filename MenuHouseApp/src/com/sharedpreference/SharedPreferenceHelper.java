package com.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreferenceHelper {

	public static final String USERNAME = "username";
	public static final String PHONE = "phonenumber";
	public static final String ADDRESS = "address";
	

	public static void savePreferences(String key, String value, Context ctx) {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreference(Context ctx, String key) {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		return sharedPreferences.getString(key, "");
	}

}
