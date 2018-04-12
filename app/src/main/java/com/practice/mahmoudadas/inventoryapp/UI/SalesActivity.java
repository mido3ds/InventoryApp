package com.practice.mahmoudadas.inventoryapp.UI;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.SalesTable;
import com.practice.mahmoudadas.inventoryapp.R;
import com.practice.mahmoudadas.inventoryapp.UI.Adapters.SalesCursorAdapter;

public class SalesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;
    private final LoaderManager loaderManager = getLoaderManager();
    private CursorAdapter adapter = new SalesCursorAdapter(this, null);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new CursorLoader(this, SalesTable.CONTENT_URI,
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
}
