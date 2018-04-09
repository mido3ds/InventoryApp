package com.practice.mahmoudadas.inventoryapp.Data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "inventory.db";

    public static final String CONTENT_AUTHORITY = "com.practice.mahmoudadas.inventoryapp";
    public static final Uri CONTENT_BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private InventoryContract() {
    }

    public static final class ItemsTable implements BaseColumns {
        // main
        public static final String TABLE_NAME = "items";
        public static final String
                COLUMN_NAME = "name",
                COLUMN_SUPPLIER_NAME = "supplierName",
                COLUMN_QUANTITY = "quantity",
                COLUMN_PRICE = "price",
                COLUMN_IMG_ID = "img";

        // Uris and Types
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_BASE_URI, TABLE_NAME);
        public static final String
                CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME,
                CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        // SQLs
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
        // main
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

        // Uris and Types
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_BASE_URI, TABLE_NAME);
        public static final String
                CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME,
                CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        // SQLs
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
