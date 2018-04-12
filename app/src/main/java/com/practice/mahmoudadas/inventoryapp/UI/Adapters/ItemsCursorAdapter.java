package com.practice.mahmoudadas.inventoryapp.UI.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public void bindView(View view, final Context context, Cursor cursor) {
        final Item item = Item.fromCursor(cursor);

        ((ImageView) view.findViewById(R.id.item_img)).setImageResource(item.getImgResourceId());
        ((TextView) view.findViewById(R.id.item_name)).setText(item.getName());
        ((TextView) view.findViewById(R.id.item_supplier)).setText("from: " + item.getSupplierName());
        ((TextView) view.findViewById(R.id.item_price)).setText(item.getPrice() + " EGP");
        ((TextView) view.findViewById(R.id.item_quantity)).setText("quantity: " + item.getQuantity());

        (view.findViewById(R.id.item_sellButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Sell All items of name: " + item.getName() + " ?")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    item.sell(item.getQuantity(), context.getContentResolver());
                                } catch (Exception e) {
                                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}
