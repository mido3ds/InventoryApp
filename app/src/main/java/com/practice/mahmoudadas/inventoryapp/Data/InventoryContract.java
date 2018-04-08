package com.practice.mahmoudadas.inventoryapp.Data;


import android.provider.BaseColumns;

public final class InventoryContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "inventory.db";

    private InventoryContract() {
    }

    public static final class ItemsTable implements BaseColumns {
        public static final String TABLE_NAME = "items";
        public static final String
                COLUMN_NAME = "name",
                COLUMN_SUPPLIER_NAME = "supplierName",
                COLUMN_QUANTITY = "quantity",
                COLUMN_PRICE = "price",
                COLUMN_IMG_ID = "img";

        static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME +
                        "(" +
                        _ID + " INT PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME + " TEXT NOT NULL," +
                        COLUMN_SUPPLIER_NAME + " TEXT NOT NULL," +
                        COLUMN_QUANTITY + " INT NOT NULL," +
                        COLUMN_PRICE + " REAL NOT NULL," +
                        COLUMN_IMG_ID + " INT" +
                        ");";
        static final String SQL_DROP_TABLE =
                "DROP TABLE " + TABLE_NAME;
    }

    public static final class SalesTable implements BaseColumns {
        public static final String TABLE_NAME = "sales";
        public static final String
                COLUMN_DATE = "date",
                COLUMN_SALE_TYPE = "saleType",
                COLUMN_ITEM_ID = "itemID",
                COLUMN_QUANTITY = "quantity",
                COLUMN_TOTAL_PRICE = "price";
        public static final int
                SALE_TYPE_SELL = 0,
                SALE_TYPE_BUY = 1;

        static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME +
                        "(" +
                        _ID + " INT PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_DATE + " INTEGER NOT NULL," +
                        COLUMN_SALE_TYPE + " INT NOT NULL," +
                        COLUMN_ITEM_ID + " INT NOT NULL," +
                        COLUMN_QUANTITY + " INT NOT NULL," +
                        COLUMN_TOTAL_PRICE + " REAL NOT NULL" +
                        ");";
        static final String SQL_DROP_TABLE =
                "DROP TABLE " + TABLE_NAME;
    }
}
