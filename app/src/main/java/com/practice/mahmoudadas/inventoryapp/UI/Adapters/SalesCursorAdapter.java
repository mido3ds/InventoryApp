package com.practice.mahmoudadas.inventoryapp.UI.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;


public class SalesCursorAdapter extends CursorAdapter {
    public SalesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // TODO
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO
    }
}
