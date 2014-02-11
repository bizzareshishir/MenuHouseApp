package com.example.menuhouseapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.adapters.ArrayAdapterItem;
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
		listHotelName = (ListView) findViewById(R.id.listHotelName);
	}

	public void onClickLogout(View view) {
		Utils.showToast("Logout Successfully", ctx);
	}

	class AsyncDownloadHotel extends AsyncTask<Void, Void, Void> {

		String cityId = "";
		String response = "";

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
			response = WebServiceUtil.getResponse(Utils.URL + Utils.CITY
					+ "?city_id=" + cityId);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			String toks[] = response.split("@");

			HotelInfo rArray[] = new HotelInfo[toks.length];

			for (int i = 0; i < toks.length; i++) {

				String[] tokChild = toks[i].split("~");
				String hotel_id = tokChild[0];
				String hotel_name = tokChild[1];
				String hotel_rating = tokChild[2];

				HotelInfo hotel = new HotelInfo();
				hotel.id = hotel_id;
				hotel.hotelName = hotel_name;
				hotel.rating = hotel_rating;
				rArray[i] = hotel;
			}
			final ArrayAdapterItem hotelModel = new ArrayAdapterItem(
					HotelListActivity.this, R.layout.list_view_row_item, rArray);

			listHotelName.setAdapter(hotelModel);

			listHotelName.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					/*
					 * Context context = view.getContext();
					 * 
					 * TextView textViewItem = ((TextView) view
					 * .findViewById(R.id.textViewItem));
					 * 
					 * // get the clicked item name String listItemText =
					 * textViewItem.getText().toString();
					 * 
					 * Toast.makeText(context, listItemText + " city Selected",
					 * Toast.LENGTH_SHORT).show();
					 */

					HotelInfo hotel = hotelModel.getItem(arg2);
					Intent intent = new Intent(ctx, DashBoardActivity.class);
					intent.putExtra("hotel_id", hotel.id);
					intent.putExtra("hotel_name", hotel.hotelName);
					startActivity(intent);
				}
			});
			super.onPostExecute(result);
		}

	}

}
