package com.practice.mahmoudadas.inventoryapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbOpenHelper extends SQLiteOpenHelper {
    public DbOpenHelper(Context context) {
        super(context, InventoryContract.DATABASE_NAME, null, InventoryContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(InventoryContract.ItemsTable.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(InventoryContract.SalesTable.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(InventoryContract.ItemsTable.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(InventoryContract.SalesTable.SQL_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
