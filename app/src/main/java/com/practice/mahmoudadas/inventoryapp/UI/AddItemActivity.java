package com.practice.mahmoudadas.inventoryapp.UI;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.practice.mahmoudadas.inventoryapp.Data.Item;
import com.practice.mahmoudadas.inventoryapp.R;

public class AddItemActivity extends AppCompatActivity {
    private EditText itemNameET;
    private EditText suppNameET;
    private EditText quantityET;
    private EditText priceET;
    private Spinner iconSpinner;

    private int imgResourceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        itemNameET = findViewById(R.id.add_itemName);
        suppNameET = findViewById(R.id.add_suppName);
        quantityET = findViewById(R.id.add_quantity);
        priceET = findViewById(R.id.add_price);
        iconSpinner = findViewById(R.id.add_img);

        setupSpinner();
    }

    private void setupSpinner() {
        // TODO
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
                try {
                    createItem().buy(getContentResolver());
                } catch (Exception e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    finish();
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Item createItem() {
        return new Item(itemNameET.getText().toString(), suppNameET.getText().toString(),
                Integer.valueOf(quantityET.getText().toString()),
                Float.valueOf(priceET.getText().toString()), imgResourceId);
    }
}
