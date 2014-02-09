package com.example.menuhouseapp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.adapters.HotelAdapter;
import com.data.HotelInfo;
import com.sharedpreference.SharedPreferenceHelper;
import com.utils.Utils;
import com.utils.WebServiceUtil;

public class HotelListActivity extends Activity {

	TextView textUserName;
	ListView listHotelName;

	Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_list_activity);

		init();

		textUserName.setText("Welcome "
				+ SharedPreferenceHelper.getPreference(ctx,
						SharedPreferenceHelper.USERNAME));
		new AsyncDownloadHotel(getIntent().getExtras().getString("city_id"))
				.execute();
	}


	private void init() {
		// TODO Auto-generated method stub
		ctx = this;

		textUserName = (TextView) findViewById(R.id.tv_welcome);
		listHotelName = (ListView) findViewById(R.id.listCityName);
	}

	public void onClickLogout(View view) {
		Utils.showToast("Logout Successfully", ctx);
	}

	class AsyncDownloadHotel extends AsyncTask<Void, Void, Void> {

		String cityId = "";
        String response="";
		public AsyncDownloadHotel(String cityId) {
			// TODO Auto-generated constructor stub
			this.cityId = cityId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			response=getResponse(Utils.URL + Utils.CITY, cityId);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			String toks[] = response.split("@");

			HotelInfo rArray[] = new HotelInfo[toks.length];

			for (int i = 0; i < toks.length; i++) {

				String[] tokChild = toks[i].split("~");
				String city_id = tokChild[0];
				String city_name = tokChild[1];
				HotelInfo city = new HotelInfo();
				city.id = city_id;
				city.hotelName = city_name;
				rArray[i] = city;
			}
			HotelAdapter cityModel = new HotelAdapter(getApplicationContext(),
					android.R.layout.simple_spinner_dropdown_item, rArray);
			
			listHotelName.setAdapter(cityModel);
			
			listHotelName.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
				}
			});
			super.onPostExecute(result);
		}

		String getResponse(String webLink, String id) {
			// TODO Auto-generated method stub
			HttpResponse response = null;
			String respString = "";
			try {

				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet();

				request.setURI(new URI(webLink + "?city_id=" + id));

				response = client.execute(request);

				respString = WebServiceUtil.convertStreamToString(response
						.getEntity().getContent());

			} catch (URISyntaxException e) {
				e.printStackTrace();
				return null;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

			return respString;

		}

	}

}
