package com.example.text;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    private ListView listView;
    private SightAdapter adapter;
    private List<Sight> sightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_main);
        iv = findViewById(R.id.image);
        listView = findViewById(R.id.listView);
        sightList = new ArrayList<>();

        // 初始化默认的风景数据
        resetSights();

        adapter = new SightAdapter(this, sightList);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sight sight = sightList.get(position);
                iv.setImageResource(sight.getPicid());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_menu_add:
                addSight(0); // 添加新风景到列表开头
                break;
            case R.id.opt_menu_reset:
                resetSights(); // 重置风景列表
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "风景列表已重置", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.ctx_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (item.getItemId()) {
            case R.id.ctx_add:
                addSight(position + 1);
                break;
            case R.id.ctx_edit:
                modifySight(position);
                break;
            case R.id.ctx_delete:
                deleteSight(position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void addSight(int position) {
        showSightDataDialog(position, null, "新增风景");
    }

    private void modifySight(int position) {
        Sight sight = sightList.get(position);
        showSightDataDialog(position, sight, "修改风景");
    }

    private void deleteSight(int position) {
        if (position >= 0 && position < sightList.size()) {
            sightList.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    private void showSightDataDialog(int position, Sight sight, String title) {
        new SightDataDialog(this, title).showDialog(sight, new SightDataDialog.OnSubmitListener() {
            @Override
            public void onSubmit(Sight updatedSight) {
                if (sight == null) {
                    sightList.add(position, updatedSight);
                } else {
                    int index = sightList.indexOf(sight);
                    if (index != -1) {
                        sight.setPicid(updatedSight.getPicid());
                        sight.setName(updatedSight.getName());
                        sight.setDescription(updatedSight.getDescription());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void resetSights() {
        sightList.clear();
        String[] defaultSightNames = getResources().getStringArray(R.array.default_sight_names);
        String[] defaultSightDescriptions = getResources().getStringArray(R.array.default_sight_descriptions);
        TypedArray defaultSightImages = getResources().obtainTypedArray(R.array.default_sight_picIds);

        for (int i = 0; i < defaultSightNames.length; i++) {
            int imageResourceId = defaultSightImages.getResourceId(i, R.drawable.hongyadong); // 默认图片资源ID
            sightList.add(new Sight(defaultSightNames[i], defaultSightDescriptions[i], imageResourceId));
        }
    }
}