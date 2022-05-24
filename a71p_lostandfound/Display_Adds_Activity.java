package com.example.a71p_lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a71p_lostandfound.data.DatabaseHelper;
import com.example.a71p_lostandfound.model.AdvertisedItem;

import java.util.ArrayList;
import java.util.List;

public class Display_Adds_Activity extends AppCompatActivity {

    DatabaseHelper db;

    ListView allAdsListView;

    ArrayList<String> adsArrayList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_adds);

        allAdsListView = findViewById(R.id.allAdsListView);

        adsArrayList = new ArrayList<>();
        db = new DatabaseHelper(Display_Adds_Activity.this);

        List<AdvertisedItem> allAdsList = db.viewAllAds();

        for (AdvertisedItem aditm: allAdsList)
        {
            adsArrayList.add(aditm.getItemName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adsArrayList);
        allAdsListView.setAdapter(adapter);

        allAdsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Remove_Add_Activity.class);

                String pikedAdd = allAdsList.get(position).getItemName();
                intent.putExtra("selectedadd", pikedAdd);

                startActivity(intent);
            }
        });
    }
}