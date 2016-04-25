package com.sam_chordas.android.stockhawk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;

import java.util.ArrayList;

/**
 * Created by kssand on 23-Apr-16.
 */
public class CustomSpinnerAdapter extends android.widget.ArrayAdapter<String> {
    private final LayoutInflater inflater;
    ArrayList<String> list;
    Context mContext;

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
       TextView category= (TextView)row.findViewById(R.id.tvCategory);
        category.setText(list.get(position));
        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView category= (TextView)row.findViewById(R.id.tvCategory);
        category.setText(list.get(position));
        return row;
    }

    public CustomSpinnerAdapter(Context context, ArrayList<String> objects) {
        super(context, R.layout.spinner_row, objects);
        mContext=context;
        list=objects;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
}
