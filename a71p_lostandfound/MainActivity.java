package com.example.a71p_lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a71p_lostandfound.data.DatabaseHelper;
import com.example.a71p_lostandfound.model.AdvertisedItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;

    Button createNew, seeAds, seeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNew = findViewById(R.id.newAddButton);
        seeAds = findViewById(R.id.seeAdsButton);
        seeMap = findViewById(R.id.seeFoundItemsMap);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(MainActivity.this, Create_Add_Activity.class);
                startActivity(createIntent);
            }
        });

        seeAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAdsIntent = new Intent(MainActivity.this, Display_Adds_Activity.class);
                startActivity(showAdsIntent);
            }
        });

        seeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seeMapIntent = new Intent(MainActivity.this, Display_Map_Activity.class);
                startActivity(seeMapIntent);
            }
        });
    }
}