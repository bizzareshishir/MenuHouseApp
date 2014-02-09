package com.example.menuhouseapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.adapters.CityAdapter;
import com.data.CityInfo;
import com.sharedpreference.SharedPreferenceHelper;
import com.utils.Utils;

public class MainActivity extends Activity {

	TextView textCityname;
	ListView listCityName;

	Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

		downloadCity();
		textCityname.setText("Welcome "
				+ SharedPreferenceHelper.getPreference(ctx,
						SharedPreferenceHelper.USERNAME));
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
		final CityAdapter cityModel = new CityAdapter(getApplicationContext(),
				android.R.layout.simple_spinner_dropdown_item, rArray);

		listCityName.setAdapter(cityModel);

		listCityName.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				CityInfo city =cityModel.getItem(arg2);
				Intent intent= new Intent(ctx,HotelListActivity.class);
				intent.putExtra("city_id", city.id);
				startActivity(intent);
				
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		ctx = this;

		textCityname = (TextView) findViewById(R.id.tv_welcome);
		listCityName = (ListView) findViewById(R.id.listCityName);
	}

	public void onClickLogout(View view) {
		Utils.showToast("Logout Successfully", ctx);
	}

}
