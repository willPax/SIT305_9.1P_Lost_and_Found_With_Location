package com.example.a71p_lostandfound.model;

import com.google.android.gms.maps.model.LatLng;

public class AdvertisedItem {

    private int add_ID;
    private String userName;
    private String userPhone;
    private String itemName;
    private String founddate;
    private double latitude;
    private double longitude;
    private int lostORfound;
    private String location;

    public AdvertisedItem(String userName, String userPhone, String itemName, String founddate, double latitude, double longitude, int lostORfound, String location) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.itemName = itemName;
        this.founddate = founddate;
        this.latitude = latitude;
        this.longitude =longitude;
        this.lostORfound = lostORfound;
        this.location = location;
    }

    public AdvertisedItem() {
    }


    public int getAdd_ID() {
        return add_ID;
    }

    public void setAdd_ID(int add_ID) {
        this.add_ID = add_ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String  getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String  userPhone) {
        this.userPhone = userPhone;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFounddate() {
        return founddate;
    }

    public void setFounddate(String founddate) {
        this.founddate = founddate;
    }

    public LatLng getGPSLocation() {
        LatLng itemLocation = new LatLng(this.latitude, this.longitude);
        return itemLocation;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public int getLostORfound() {
        return lostORfound;
    }

    public void setLostORfound(int lostORfound) {
        this.lostORfound = lostORfound;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}


