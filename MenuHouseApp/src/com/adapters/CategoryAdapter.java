package com.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.data.CategoryData;
import com.data.CityInfo;

public class CategoryAdapter extends ArrayAdapter<CategoryData>{
	
	
	Context context;  
	CategoryData data[] = null;
    
    public CategoryAdapter(Context context, int layoutResourceId, CategoryData[] data) {
        super(context, layoutResourceId, data);
      
        this.context=context;
        this.data = data;
    }	
    
    public int getCount(){
        return data.length;
     }

     public CategoryData getItem(int position){
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
        if(data[position]!=null)
        label.setText(data[position].category);

        return label;		
		
    }
    
    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
    	TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTypeface(null,Typeface.BOLD);
        label.setTextSize(20);
        if(data[position]!=null)
        label.setText(data[position].category);

        return label;
    }   

}
