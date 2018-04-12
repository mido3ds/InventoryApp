package com.practice.mahmoudadas.inventoryapp.UI;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.SalesTable;
import com.practice.mahmoudadas.inventoryapp.UI.Adapters.SalesCursorAdapter;

public class SalesActivity extends ListBaseActivity {
    public SalesActivity() {
        adapter = new SalesCursorAdapter(this, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new CursorLoader(this, SalesTable.CONTENT_URI,
                null, null, null, null);
    }
}
