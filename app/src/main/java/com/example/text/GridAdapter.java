package com.example.text;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<Sight> sightList;

    public GridAdapter(Context context, List<Sight> sightList) {
        this.context = context;
        this.sightList = sightList;
    }

    @Override
    public int getCount() {
        return sightList.size();
    }

    @Override
    public Object getItem(int position) {
        return sightList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_view, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.grid_view_iv);
            holder.textViewName = convertView.findViewById(R.id.grid_view_name);
            holder.textViewDes = convertView.findViewById(R.id.grid_view_des);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Sight sight = sightList.get(position);
        holder.imageView.setImageResource(sight.getPicid());
        holder.textViewName.setText(sight.getName());
        holder.textViewDes.setText(sight.getDescription());

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewDes;
    }
}