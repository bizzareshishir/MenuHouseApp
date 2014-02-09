package com.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.data.CityInfo;

public class CityAdapter extends ArrayAdapter<CityInfo>{
	
	
	Context context;  
	CityInfo data[] = null;
    
    public CityAdapter(Context context, int layoutResourceId, CityInfo[] data) {
        super(context, layoutResourceId, data);
      
        this.context=context;
        this.data = data;
    }	
    
    public int getCount(){
        return data.length;
     }

     public CityInfo getItem(int position){
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
        label.setText(data[position].cityName);

        return label;		
		
    }
    
    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
    	TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTypeface(null,Typeface.BOLD);
        label.setTextSize(20);
        label.setText(data[position].cityName);

        return label;
    }   

}
