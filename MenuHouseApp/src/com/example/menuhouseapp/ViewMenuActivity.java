package com.example.menuhouseapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.adapters.CategoryAdapter;
import com.data.CategoryData;
import com.data.MenuData;
import com.utils.Utils;
import com.utils.WebServiceUtil;

public class ViewMenuActivity extends Activity {

	TextView txtUserName, txtHotelName;
	LinearLayout ll_menu;
	Context ctx;

	String hotelId, hotelName;
	Spinner spinCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_menu_activity);

		init();
		hotelId = getIntent().getExtras().getString("hotel_id");
		hotelName = getIntent().getExtras().getString("hotel_name");
		txtUserName.setText(Utils.userName(ctx));
		txtHotelName.setText(hotelName);

		new AsyncDownloadCategory(hotelId).execute();
	}

	private void init() {
		// TODO Auto-generated method stub
		txtUserName = (TextView) findViewById(R.id.txtUserName);
		txtHotelName = (TextView) findViewById(R.id.txtHotelName);
		ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
		spinCategory = (Spinner) findViewById(R.id.spinCategory);
		ctx = this;
	}

	public void onClickLogout(View view) {
		Utils.showToast("Logout Successfully", ctx);
	}

	public LinearLayout returnLayout(MenuData menuInfo) {

		final LinearLayout lytContainer = (LinearLayout) View.inflate(this,
				R.layout.view_menu_row, null);

		final TextView txt_srno = (TextView) lytContainer
				.findViewById(R.id.txt_srno);
		final TextView txt_items = (TextView) lytContainer
				.findViewById(R.id.txt_items);
		final TextView txt_price = (TextView) lytContainer
				.findViewById(R.id.txt_price);

		txt_srno.setText(menuInfo.id);
		txt_items.setText(menuInfo.items);
		txt_price.setText(menuInfo.price);

		return lytContainer;
	}

	class AsyncDownloadCategory extends AsyncTask<Void, Void, Void> {

		String hotelId = "";
		String response = "";

		public AsyncDownloadCategory(String cityId) {
			// TODO Auto-generated constructor stub
			this.hotelId = cityId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			response = WebServiceUtil.getResponse(Utils.URL + Utils.CATEGORY
					+ "?hotel_id=" + hotelId);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			response = response.trim();
			String toks[] = response.split("@");
			CategoryData rArray[] = new CategoryData[toks.length];

			for (int i = 0; i < toks.length; i++) {
				String[] tokChild = toks[i].split("~");
				if (tokChild.length == 2) {
					String id = tokChild[0];
					String cat_name = tokChild[1];
					CategoryData categObj = new CategoryData();
					categObj.id = id;
					categObj.category = cat_name;
					rArray[i] = categObj;
				}

			}

			final CategoryAdapter cityModel = new CategoryAdapter(
					getApplicationContext(),
					android.R.layout.simple_spinner_dropdown_item, rArray);
			spinCategory.setAdapter(cityModel);
			spinCategory
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub

							CategoryData catData = cityModel.getItem(arg2);
							new AsyncDownloadMenu(hotelId, catData.id)
									.execute();
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

			super.onPostExecute(result);
		}

	}

	class AsyncDownloadMenu extends AsyncTask<Void, Void, Void> {

		String hotelId, catId;

		String response = "";

		public AsyncDownloadMenu(String hotelId, String catId) {
			// TODO Auto-generated constructor stub
			this.hotelId = hotelId;
			this.catId = catId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			ll_menu.removeAllViews();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			response = WebServiceUtil.getResponse(Utils.URL + Utils.MENU
					+ "?hotel_id=" + hotelId + "&&cat_id=" + catId);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Log.e("newlog", response);
			response = response.trim();
			String toks[] = response.split("@");
			MenuData rArray[] = new MenuData[toks.length];

			for (int i = 0; i < toks.length; i++) {
				String[] tokChild = toks[i].split("~");
				String id = tokChild[0];
				String item_name = tokChild[1];
				String price = tokChild[2];
				MenuData menuObj = new MenuData();
				int k=i;
				menuObj.id = (k+1)+"";
				menuObj.items = item_name;
				menuObj.price = price;
				rArray[i] = menuObj;
				
				ll_menu.addView(returnLayout(menuObj));
			}

			
			super.onPostExecute(result);

		}

	}
}
