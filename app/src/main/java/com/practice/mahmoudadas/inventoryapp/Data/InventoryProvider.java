package com.practice.mahmoudadas.inventoryapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.ItemsTable;
import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.SalesTable;


public final class InventoryProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int
            ITEMS = 0,
            ITEMS_ID = 1,
            SALES = 2,
            SALES_ID = 3;

    static {
        uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, ItemsTable.TABLE_NAME, ITEMS);
        uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, ItemsTable.TABLE_NAME + "/#", ITEMS_ID);

        uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, SalesTable.TABLE_NAME, SALES);
        uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, SalesTable.TABLE_NAME + "/#", SALES_ID);
    }

    private DbOpenHelper openHelper;

    @Override
    public boolean onCreate() {
        openHelper = new DbOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        int match = uriMatcher.match(uri);
        String tableName;

        switch (match) {
            case ITEMS:
                tableName = ItemsTable.TABLE_NAME;
                break;
            case ITEMS_ID:
                tableName = ItemsTable.TABLE_NAME;
                selection = ItemsTable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            case SALES:
                tableName = SalesTable.TABLE_NAME;
                break;
            case SALES_ID:
                tableName = SalesTable.TABLE_NAME;
                selection = SalesTable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            default:
                throw new IllegalArgumentException("invalid uri " + uri);
        }

        Cursor cursor = openHelper.getReadableDatabase().
                query(tableName, projection, selection,
                        selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return ItemsTable.CONTENT_LIST_TYPE;
            case ITEMS_ID:
                return ItemsTable.CONTENT_ITEM_TYPE;
            case SALES:
                return SalesTable.CONTENT_LIST_TYPE;
            case SALES_ID:
                return SalesTable.COLUMN_ITEM_ID;
            default:
                throw new IllegalArgumentException("invalid uri " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int match = uriMatcher.match(uri);
        String tableName;

        switch (match) {
            case ITEMS:
                tableName = ItemsTable.TABLE_NAME;
                break;
            case SALES:
                tableName = SalesTable.TABLE_NAME;
                break;
            case SALES_ID:
            case ITEMS_ID:
                throw new IllegalArgumentException("invalid uri for insertion " + uri);
            default:
                throw new IllegalArgumentException("invalid uri " + uri);
        }

        long i = openHelper.getWritableDatabase().
                insert(tableName, null, contentValues);

        if (i != -1) {
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.withAppendedPath(uri, String.valueOf(i));
    }

    @Override
    public int delete(Uri uri,
                      String selection,
                      String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        String tableName;

        switch (match) {
            case ITEMS:
                tableName = ItemsTable.TABLE_NAME;
                selection = "1"; // to get a count of removed rows
                break;
            case ITEMS_ID:
                tableName = ItemsTable.TABLE_NAME;
                selection = ItemsTable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            case SALES:
                tableName = SalesTable.TABLE_NAME;
                selection = "1"; // to get a count of removed rows
                break;
            case SALES_ID:
                tableName = SalesTable.TABLE_NAME;
                selection = SalesTable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            default:
                throw new IllegalArgumentException("invalid uri " + uri);
        }

        int rows = openHelper.getWritableDatabase().delete(tableName, selection, selectionArgs);
        if (rows != 0) {
            // removed something
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        String tableName;

        switch (match) {
            case ITEMS_ID:
                tableName = ItemsTable.TABLE_NAME;
                selection = ItemsTable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            case SALES_ID:
                tableName = SalesTable.TABLE_NAME;
                selection = SalesTable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            case ITEMS:
            case SALES:
                throw new IllegalArgumentException("invalid uri for updating " + uri);
            default:
                throw new IllegalArgumentException("invalid uri " + uri);
        }

        int rows = openHelper.getWritableDatabase().update(tableName, values, selection, selectionArgs);

        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }
}
