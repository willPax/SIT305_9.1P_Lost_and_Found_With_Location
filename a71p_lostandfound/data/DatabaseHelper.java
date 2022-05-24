package com.example.a71p_lostandfound.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a71p_lostandfound.model.AdvertisedItem;
import com.example.a71p_lostandfound.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ADS_TABLE = ( "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.ADD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.USERNAME + " TEXT , " + Util.PHONENUMBER + " TEXT , " + Util.ITEMNAME + " TEXT , " + Util.DATE + " TEXT , " + Util.LAT + " DOUBLE , " + Util.LNG + " DOUBLE , " + Util.LOSTFOUND + " INTEGER , " + Util.LOCATION + " STRING) " );
        db.execSQL(CREATE_ADS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + new String[]{Util.TABLE_NAME};
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public long insertNewUser(AdvertisedItem addItem){

        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.ITEMNAME, addItem.getItemName());
        contentValues.put(Util.PHONENUMBER, addItem.getUserPhone());
        contentValues.put(Util.USERNAME, addItem.getUserName());
        contentValues.put(Util.DATE, addItem.getFounddate());
        contentValues.put(Util.LAT, addItem.getLatitude());
        contentValues.put(Util.LNG, addItem.getLongitude());
        contentValues.put(Util.LOSTFOUND, addItem.getLostORfound());
        contentValues.put(Util.LOCATION, addItem.getLocation());
        long newRowId = sqldb.insert(Util.TABLE_NAME,null, contentValues);
        sqldb.close();
        return  newRowId;
    }


    public int findAddNumber(String addName)
    {
        int addIdNumber = -1;
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = sqldb.rawQuery(selectAll, null);

        if (cursor.moveToFirst())
        {
            do
            {
                if (cursor.getString(3).equalsIgnoreCase(addName))
                {
                    addIdNumber = cursor.getInt(0);
                }
            }
            while (cursor.moveToNext());
        }
        if (addIdNumber == - 1){return 0;}
        else{
        return addIdNumber;}
    }

    public AdvertisedItem findItem(int addId)
    {
        AdvertisedItem foundAdd = new AdvertisedItem();
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = sqldb.rawQuery(selectAll, null);

        if (cursor.moveToFirst())
        {
            do
            {
                if (cursor.getInt(0) == addId)
                    {
                        AdvertisedItem advItem = new AdvertisedItem();
                        advItem.setAdd_ID(cursor.getInt(0));
                        advItem.setUserName(cursor.getString(1));
                        advItem.setUserPhone(cursor.getString(2));
                        advItem.setItemName(cursor.getString(3));
                        advItem.setFounddate(cursor.getString(4));
                        advItem.setLatitude(cursor.getDouble(5));
                        advItem.setLongitude(cursor.getDouble(6));
                        advItem.setLostORfound(cursor.getInt(7));
                        advItem.setLocation(cursor.getString(8));

                        foundAdd = advItem;
                    }
            }
            while (cursor.moveToNext());
        }
        return foundAdd;
    }

    public List<AdvertisedItem> viewAllAds()
    {
        List<AdvertisedItem> adsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst())
        {
            do {
                AdvertisedItem advItem = new AdvertisedItem();
                advItem.setAdd_ID(cursor.getInt(0));
                advItem.setUserName(cursor.getString(1));
                advItem.setUserPhone(cursor.getString(2));
                advItem.setItemName(cursor.getString(3));
                advItem.setFounddate(cursor.getString(4));
                advItem.setLatitude(cursor.getDouble(5));
                advItem.setLongitude(cursor.getDouble(6));
                advItem.setLostORfound(cursor.getInt(7));
                advItem.setLocation(cursor.getString(8));
                adsList.add(advItem);
            } while (cursor.moveToNext());
        }
        return  adsList;
    }

    public void removeAdd(int addId) {

        SQLiteDatabase sqldb = this.getWritableDatabase();
        sqldb.execSQL("DELETE FROM " + Util.TABLE_NAME+ " WHERE "+ Util.ADD_ID + "='" + addId+ "'");
        sqldb.close();

    }
}
