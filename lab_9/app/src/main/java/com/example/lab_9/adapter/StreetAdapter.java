package com.example.lab_9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.lab_9.R;
import com.example.lab_9.model.Street;

public class StreetAdapter extends BaseAdapter {
    private final List<Street> streets;
    private final LayoutInflater inflater;

    public StreetAdapter(Context context, List<Street> streets) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_street, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Street street = streets.get(position);
        holder.bind(street);

        return convertView;
    }

    private static class ViewHolder {
        private final TextView nameView;
        private final TextView cityView;
        private final ImageView imageView;

        ViewHolder(View view) {
            nameView = view.findViewById(R.id.streetName);
            cityView = view.findViewById(R.id.streetCity);
            imageView = view.findViewById(R.id.streetImage);
        }

        void bind(Street street) {
            nameView.setText(street.getName());
            cityView.setText(street.getCity());
            imageView.setImageResource(R.drawable.street1);
        }
    }
}