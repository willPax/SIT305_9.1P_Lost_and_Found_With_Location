package com.example.a71p_lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.a71p_lostandfound.data.DatabaseHelper;
import com.example.a71p_lostandfound.model.AdvertisedItem;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class Create_Add_Activity extends AppCompatActivity {
    DatabaseHelper db;
    private static final String API_KEY = "AIzaSyA8YY4UjyDvzxL9KOYpea8uB8sX-rhAWyU";

    EditText userName, userPhone, userItemName, foundDate, foundLoc;
    Button saveItem, useCurrentLocation;
    RadioGroup radioGroup;
    RadioButton lostItem, foundItem;

    LocationManager locationManager;
    LocationListener locationListener;

    double ITEM_LAT = 0;
    double ITEM_LONG = 0;
    String ITEM_LOCATION;
    int LOST_FOUND;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_add);

        userName = findViewById(R.id.userEnteredName);
        userPhone = findViewById(R.id.userEnteredPhone);
        userItemName = findViewById(R.id.userEnteredItem);
        foundDate = findViewById(R.id.itemFoundDateEditText);
        foundLoc = findViewById(R.id.locationFoundEditText);
        useCurrentLocation = findViewById(R.id.useCurrentLocationButton);
        saveItem = findViewById(R.id.saveAddButton);
        radioGroup = findViewById(R.id.radioGroup);
        lostItem = findViewById(R.id.lostRadioButton);
        foundItem = findViewById(R.id.foundRadioButton);
        db = new DatabaseHelper(this);

        Places.initialize(getApplicationContext(), API_KEY);
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                ITEM_LAT = place.getLatLng().latitude;
                ITEM_LONG = place.getLatLng().longitude;
                ITEM_LOCATION = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(Create_Add_Activity.this, "Error! "+ status, Toast.LENGTH_SHORT).show();
            }

        });

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                foundLoc.setText(location.toString());

                useCurrentLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ITEM_LAT = location.getLatitude();
                        ITEM_LONG= location.getLongitude();
                        ITEM_LOCATION = " ";
                    }
                });
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }


        lostItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOST_FOUND = 0;
            }
        });
        foundItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOST_FOUND = 1;
            }
        });

        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = userName.getText().toString();
                String useritem = userItemName.getText().toString();
                String phonenumber = userPhone.getText().toString();
                String founddate = foundDate.getText().toString();
                int lostorfound = LOST_FOUND;
                double foundLat = ITEM_LAT;
                double foundLong = ITEM_LONG;
                String loc = ITEM_LOCATION;

                if (lostorfound == 0 || lostorfound == 1)
                {
                    if (foundLat != 0 && foundLong != 0)
                    {
                        long result = db.insertNewUser(new AdvertisedItem(username, phonenumber, useritem, founddate, foundLat, foundLong, lostorfound, loc));
                        {
                            if (result > 0) {
                                Intent intent = new Intent(Create_Add_Activity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(Create_Add_Activity.this, "Error Adding Item to Database", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void radioLostSelected(View view) {
        LOST_FOUND = 0;
    }

    public void radioFoundSelected(View view) {
        LOST_FOUND = 1;
    }
}