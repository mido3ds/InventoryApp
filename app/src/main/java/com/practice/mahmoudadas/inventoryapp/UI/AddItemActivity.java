package com.practice.mahmoudadas.inventoryapp.UI;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract;
import com.practice.mahmoudadas.inventoryapp.Data.Item;
import com.practice.mahmoudadas.inventoryapp.R;

public class AddItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.additem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                insertItem(createItem());
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertItem(Item item) {
        getContentResolver().insert(InventoryContract.ItemsTable.CONTENT_URI, item.toContentValues()); // TODO: check for exceptions
    }

    private Item createItem() {
        return null; // TODO: create item from data the user added
    }
}
