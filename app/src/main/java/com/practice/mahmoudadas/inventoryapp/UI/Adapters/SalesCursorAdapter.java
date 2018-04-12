package com.practice.mahmoudadas.inventoryapp.UI.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.practice.mahmoudadas.inventoryapp.Data.Sale;
import com.practice.mahmoudadas.inventoryapp.R;

import java.util.Date;


public class SalesCursorAdapter extends CursorAdapter {
    public SalesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_sale, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Sale sale = Sale.fromCursor(cursor);

        ((TextView) view.findViewById(R.id.sale_date)).setText("Date: " + new Date(sale.getDate()).toString()); // TODO: check datetime format
        ((TextView) view.findViewById(R.id.sale_itemid)).setText("Item-id: " + sale.getItemId());
        ((TextView) view.findViewById(R.id.sale_quantity)).setText("Quantity: " + sale.getQuantity());
        ((TextView) view.findViewById(R.id.sale_totalprice)).setText("Total-Price: " + sale.getTotalPrice());
        ((TextView) view.findViewById(R.id.sale_type)).setText(Sale.typeToString(sale.getType()));
    }
}
