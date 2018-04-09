package com.practice.mahmoudadas.inventoryapp.UI;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.mahmoudadas.inventoryapp.Data.Item;
import com.practice.mahmoudadas.inventoryapp.R;


public class ItemsCursorAdapter extends CursorAdapter {
    public ItemsCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_stored_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Item item = Item.fromCursor(cursor);

        ((ImageView) view.findViewById(R.id.img)).setImageResource(item.getImgResourceId());
        ((TextView) view.findViewById(R.id.name)).setText(item.getName());
        ((TextView) view.findViewById(R.id.supplier)).append(item.getSupplierName());
        ((TextView) view.findViewById(R.id.price)).setText(item.getPrice() + " EGP");
        ((TextView) view.findViewById(R.id.quantity)).append(String.valueOf(item.getQuantity()));

        // TODO: listen for sellButton
    }
}
