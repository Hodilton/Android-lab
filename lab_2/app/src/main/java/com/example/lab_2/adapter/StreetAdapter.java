package com.example.lab_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.lab_2.R;
import com.example.lab_2.model.Street;

public class StreetAdapter extends BaseAdapter {
    private final Context context;
    private final List<Street> streets;
    private final LayoutInflater inflater;

    public StreetAdapter(Context context, List<Street> streets) {
        this.context = context;
        this.streets = streets;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() { return streets.size(); }
    @Override
    public Object getItem(int position) { return streets.get(position); }
    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_street, parent, false);
        }

        TextView nameView = convertView.findViewById(R.id.streetName);
        TextView lengthView = convertView.findViewById(R.id.streetLength);
        ImageView imageView = convertView.findViewById(R.id.streetImage);

        Street street = streets.get(position);

        nameView.setText(street.getName());
        lengthView.setText("Длина: " + street.getLength() + " м");
        imageView.setImageResource(street.getImageResourceId());

        return convertView;
    }
}