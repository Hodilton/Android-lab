package com.example.lab_8.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab_8.R;

import java.util.List;

public class FileAdapter extends BaseAdapter {
    private final List<String> files;
    private final LayoutInflater inflater;

    public FileAdapter(Context context, List<String> files) {
        this.files = files;
        this.inflater = LayoutInflater.from(context);
    }

    public void updateList(List<String> newList) {
        if (newList != null) {
            files.clear();
            files.addAll(newList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() { return files.size(); }
    @Override
    public Object getItem(int position) { return files.get(position); }
    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_file, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String fileName = files.get(position);
        holder.bind(fileName);

        return convertView;
    }

    private static class ViewHolder {
        private final TextView nameView;
        private final ImageView iconView;

        ViewHolder(View view) {
            nameView = view.findViewById(R.id.fileName);
            iconView = view.findViewById(R.id.fileIcon);
        }

        void bind(String fileName) {
            nameView.setText(fileName);
            iconView.setImageResource(R.drawable.note);
        }
    }
}