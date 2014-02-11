package com.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.data.HotelInfo;
import com.example.menuhouseapp.R;

public class ArrayAdapterItem extends ArrayAdapter<HotelInfo> {

	Context mContext;
	int layoutResourceId;
	HotelInfo[] data = null;

	public ArrayAdapterItem(Context mContext, int layoutResourceId,
			HotelInfo[] data) {

		super(mContext, layoutResourceId, data);

		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			// inflate the layout
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		// object item based on the position
		HotelInfo objectItem = data[position];

		// get the TextView and then set the text (item name) and tag (item ID)
		// values
		TextView textViewItem = (TextView) convertView
				.findViewById(R.id.textViewItem);
		if (objectItem.hotelName != null)
			textViewItem.setText(objectItem.hotelName);
		textViewItem.setTag(objectItem.id);

		RatingBar ratingBarHotel = (RatingBar) convertView
				.findViewById(R.id.ratingBarHotel);
		try {
			Log.e("newlog", Float.parseFloat(objectItem.rating)+"");
			ratingBarHotel.setRating(Float.parseFloat(objectItem.rating));	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return convertView;

	}

}
