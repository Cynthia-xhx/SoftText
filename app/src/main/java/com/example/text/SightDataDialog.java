package com.example.text;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SightDataDialog {
    private Context context;
    private String title;

    public interface OnSubmitListener {
        void onSubmit(Sight updateSight);
    }

    public SightDataDialog(Context context, String title) {
        this.context = context;
        this.title = title;
    }

    public void showDialog(Sight sight, OnSubmitListener l) {
        Sight temp = new Sight("", "", R.drawable.hongyadong);

        if (sight != null) {
            temp.setPicid(sight.getPicid());
            temp.setName(sight.getName());
            temp.setDescription(sight.getDescription());
        }

        View v = LayoutInflater.from(context).inflate(R.layout.dialog_view, null, false);
        ImageView iv_img = v.findViewById(R.id.dialog_view_img);
        TextView tv_name = v.findViewById(R.id.dialog_view_name);
        TextView tv_des = v.findViewById(R.id.dialog_view_des);
        GridView gridView = v.findViewById(R.id.grid_view);

        iv_img.setImageResource(temp.getPicid());
        tv_name.setText(temp.getName());
        tv_des.setText(temp.getDescription());

        // 初始化 sightList
        List<Sight> sightList = new ArrayList<>();
        String[] sightNames = context.getResources().getStringArray(R.array.sight_names);
        String[] sightDescriptions = context.getResources().getStringArray(R.array.sight_descriptions);
        TypedArray sightImages = context.getResources().obtainTypedArray(R.array.sight_picIds);

        for (int i = 0; i < sightNames.length; i++) {
            int imageResourceId = sightImages.getResourceId(i, R.drawable.hongyadong); // 默认图片资源ID
            sightList.add(new Sight(sightNames[i], sightDescriptions[i], imageResourceId));
        }

        // 设置 GridView 的适配器
        GridAdapter gridAdapter = new GridAdapter(context, sightList);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sight selectedSight = sightList.get(position);
                temp.setPicid(selectedSight.getPicid());
                temp.setName(selectedSight.getName());
                temp.setDescription(selectedSight.getDescription());
                iv_img.setImageResource(selectedSight.getPicid());
                tv_name.setText(selectedSight.getName());
                tv_des.setText(selectedSight.getDescription());
            }
        });

        AlertDialog.Builder bl = new AlertDialog.Builder(context);
        bl.setTitle(title);
        bl.setView(v);
        bl.setNegativeButton("取消", null);
        bl.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Sight updatedSight = new Sight(
                        tv_name.getText().toString(),
                        tv_des.getText().toString(),
                        temp.getPicid());
                if (l != null) {
                    l.onSubmit(updatedSight);
                }
            }
        });
        bl.show();
    }
}