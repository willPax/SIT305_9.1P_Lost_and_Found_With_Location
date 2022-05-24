package com.example.a71p_lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a71p_lostandfound.data.DatabaseHelper;
import com.example.a71p_lostandfound.model.AdvertisedItem;

public class Remove_Add_Activity extends AppCompatActivity {
    DatabaseHelper db;

    TextView itemName;
    TextView itemDate;
    TextView itemLocation;
    TextView headerText;

    Button deleteAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_add);

        db = new DatabaseHelper(this);
        deleteAddButton = findViewById(R.id.deleteAddButton);

        itemName = findViewById(R.id.itemTextView);
        itemDate = findViewById(R.id.locationTextView);
        itemLocation = findViewById(R.id.dateTextView);
        headerText = findViewById(R.id.headerTextView);

        Intent intent = getIntent();
        String  reievedItemName = intent.getStringExtra("selectedadd");

        itemName.setText(reievedItemName);

        int foundAddNumber = db.findAddNumber(reievedItemName);

        AdvertisedItem recievedAdd = db.findItem(foundAddNumber);

        itemName.setText(recievedAdd.getItemName());
        itemDate.setText(recievedAdd.getFounddate());
        itemLocation.setText(recievedAdd.getLocation());

        if(recievedAdd.getLostORfound()==1){headerText.setText("Found Item");}
        if(recievedAdd.getLostORfound()==0){headerText.setText("Lost Item");}

        deleteAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.removeAdd(foundAddNumber);
                Intent intent1 = new Intent(Remove_Add_Activity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}