package com.practice.mahmoudadas.inventoryapp.UI;

import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.ItemsTable;
import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.SalesTable;
import com.practice.mahmoudadas.inventoryapp.R;
import com.practice.mahmoudadas.inventoryapp.UI.Adapters.ItemsCursorAdapter;

public class ItemsMainActivity extends ListBaseActivity {
    public ItemsMainActivity() {
        adapter = new ItemsCursorAdapter(this, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                startActivity(new Intent(ItemsMainActivity.this, AddItemActivity.class));
                return true;
            case R.id.show_sales:
                startActivity(new Intent(ItemsMainActivity.this, SalesActivity.class));
                return true;
            case R.id.reset_database:
                resetDb();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetDb() {
        new AlertDialog.Builder(this)
                .setTitle("Reset All Data?")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getContentResolver().delete(ItemsTable.CONTENT_URI, null, null);
                        getContentResolver().delete(SalesTable.CONTENT_URI, null, null);
                    }
                })
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // nothing
                    }
                })
                .create()
                .show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new CursorLoader(this, ItemsTable.CONTENT_URI,
                null, null, null, null);
    }
}
