package com.practice.mahmoudadas.inventoryapp.UI;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.ItemsTable;
import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.SalesTable;
import com.practice.mahmoudadas.inventoryapp.R;
import com.practice.mahmoudadas.inventoryapp.UI.Adapters.ItemsCursorAdapter;

public class ItemsMainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 0;
    private final LoaderManager loaderManager = getLoaderManager();
    private CursorAdapter adapter = new ItemsCursorAdapter(this, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        setupListView();
    }

    protected void setupListView() {
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setEmptyView(findViewById(R.id.emptyView));
    }

    @Override
    protected void onStart() {
        super.onStart();
        runLoader();
    }

    private void runLoader() {
        Loader loader = loaderManager.getLoader(LOADER_ID);
        if (loader != null && loader.isReset()) {
            loaderManager.restartLoader(LOADER_ID, null, this);
        } else {
            loaderManager.initLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new CursorLoader(this, ItemsTable.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
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
}
