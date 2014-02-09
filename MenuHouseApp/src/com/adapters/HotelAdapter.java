package com.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.data.HotelInfo;

public class HotelAdapter extends ArrayAdapter<HotelInfo>{
	
	
	Context context;  
	HotelInfo data[] = null;
    
    public HotelAdapter(Context context, int layoutResourceId, HotelInfo[] data) {
        super(context, layoutResourceId, data);
      
        this.context=context;
        this.data = data;
    }	
    
    public int getCount(){
        return data.length;
     }

     public HotelInfo getItem(int position){
        return data[position];
     }

     public long getItemId(int position){
        return position;
     }
    
     
     @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTypeface(null,Typeface.BOLD);
        label.setTextSize(20);
        label.setText(data[position].hotelName);

        return label;		
		
    }
    
    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
    	TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTypeface(null,Typeface.BOLD);
        label.setTextSize(20);
        label.setText(data[position].hotelName);

        return label;
    }   

}
