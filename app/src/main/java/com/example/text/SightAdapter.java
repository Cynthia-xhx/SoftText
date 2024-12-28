package com.example.text;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SightAdapter extends ArrayAdapter<Sight> {

    private Context context;
    private List<Sight> list;
    public SightAdapter(@NonNull Context context, List<Sight> list) {
        super(context,android.R.layout.simple_list_item_1, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(context).inflate(R.layout.row_view,null,false);
        }
        ImageView imageView = v.findViewById(R.id.row_view_iv);
        TextView name = v.findViewById(R.id.row_view_tv_name);
        TextView description = v.findViewById(R.id.row_view_tv_des);

        Sight sight = list.get(position);
        imageView.setImageResource(sight.getPicid());
        name.setText(sight.getName());
        description.setText(sight.getDescription());
        return v;
    }
}
