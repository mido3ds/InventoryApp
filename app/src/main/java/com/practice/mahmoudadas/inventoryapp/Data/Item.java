package com.practice.mahmoudadas.inventoryapp.Data;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.ItemsTable;
import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.SalesTable;

import java.util.Date;

public class Item {
    private int id = 1;
    private String name = "";
    private String supplierName = "";
    private int quantity = 0;
    private float price = 0;
    private int imgResourceId = 0;

    public Item(int id, String name, String supplierName, int quantity, float price, int imgResourceId) {
        setId(id);
        setName(name);
        setSupplierName(supplierName);
        setQuantity(quantity);
        setPrice(price);
        setImgResourceId(imgResourceId);
    }

    public Item(String name, String supplierName, int quantity, float price, int imgResourceId) {
        setName(name);
        setSupplierName(supplierName);
        setQuantity(quantity);
        setPrice(price);
        setImgResourceId(imgResourceId);
    }

    public Item() {
        // empty
    }

    public static Item fromCursor(Cursor cursor) {
        checkIsValidCursor(cursor);
        int validColumnsInCursor = 0;
        Item item = new Item();

        int idIndex = cursor.getColumnIndex(ItemsTable._ID);
        if (idIndex != -1) {
            validColumnsInCursor++;
            item.setId(cursor.getInt(idIndex));
        }

        int nameIndex = cursor.getColumnIndex(ItemsTable.COLUMN_NAME);
        if (nameIndex != -1) {
            validColumnsInCursor++;
            item.setName(cursor.getString(nameIndex));
        }

        int suppIndex = cursor.getColumnIndex(ItemsTable.COLUMN_SUPPLIER_NAME);
        if (suppIndex != -1) {
            validColumnsInCursor++;
            item.setSupplierName(cursor.getString(suppIndex));
        }

        int quanIndex = cursor.getColumnIndex(ItemsTable.COLUMN_QUANTITY);
        if (quanIndex != -1) {
            validColumnsInCursor++;
            item.setQuantity(cursor.getInt(quanIndex));
        }

        int priceIndex = cursor.getColumnIndex(ItemsTable.COLUMN_PRICE);
        if (priceIndex != -1) {
            validColumnsInCursor++;
            item.setPrice(cursor.getFloat(priceIndex));
        }

        int imgIndex = cursor.getColumnIndex(ItemsTable.COLUMN_IMG_ID);
        if (imgIndex != -1) {
            validColumnsInCursor++;
            item.setImgResourceId(cursor.getInt(imgIndex));
        }

        if (validColumnsInCursor == 0) {
            throw new IllegalArgumentException("cursor doesn't have necessary columns");
        }
        return item;
    }

    public static void checkIsValidCursor(Cursor cursor) {
        if (cursor == null) {
            throw new IllegalArgumentException("cursor is null");
        } else if (cursor.isClosed()) {
            throw new IllegalArgumentException("cursor is closed");
        } else if (cursor.getCount() == 0) {
            throw new IllegalArgumentException("empty cursor");
        } else if (cursor.getPosition() < 0 || cursor.isAfterLast()) {
            throw new IllegalArgumentException("move cursor to a position");
        }
    }

    public static boolean isValidId(int id) {
        return id > 0;
    }

    public static boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    public static boolean isValidQuantity(int quantity) {
        return isValidPrice(quantity);
    }

    public static boolean isValidPrice(float price) {
        return price >= 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (!isValidId(id))
            throw new IllegalArgumentException("id must be grater than zero");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("name cant be empty");
        }
        this.name = name;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        if (!isValidName(supplierName)) {
            throw new IllegalArgumentException("supplier name cant be empty");
        }
        this.supplierName = supplierName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (!isValidQuantity(quantity)) {
            throw new IllegalArgumentException("quantity cant be negative");
        }
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (!isValidPrice(price)) {
            throw new IllegalArgumentException("price must be positive");
        }
        this.price = price;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public void setImgResourceId(int imgResourceId) {
        this.imgResourceId = imgResourceId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ItemsTable.COLUMN_NAME, name);
        values.put(ItemsTable.COLUMN_SUPPLIER_NAME, supplierName);
        values.put(ItemsTable.COLUMN_QUANTITY, quantity);
        values.put(ItemsTable.COLUMN_PRICE, price);
        values.put(ItemsTable.COLUMN_IMG_ID, imgResourceId);

        return values;
    }

    public void sell(int quantity, ContentResolver contentResolver) throws Exception {
        if (quantity > this.quantity) {
            throw new IllegalArgumentException("quantity is larger than available");
        } else if (contentResolver == null) {
            throw new IllegalArgumentException("contentResolver is null");
        }

        Uri uri = ContentUris.withAppendedId(ItemsTable.CONTENT_URI, id);
        int rows;
        long time = new Date().getTime();

        if (quantity == this.quantity) {
            this.quantity = 0;
            rows = contentResolver.delete(uri, null, null);
        } else {
            this.quantity -= quantity;
            rows = contentResolver.update(uri, toContentValues(), null, null);
        }

        if (rows != 1) {
            this.quantity += quantity;
            throw new Exception("item not stored in db");
        }

        new Sale(time, SalesTable.SALE_TYPE_SELL,
                id, quantity, price * quantity)
                .saveSale(contentResolver);
    }

    public void buy(ContentResolver contentResolver) throws Exception {
        if (contentResolver == null) {
            throw new IllegalArgumentException("contentResolver is null");
        }

        Cursor cursor = contentResolver.query(
                ItemsTable.CONTENT_URI,
                null,
                ItemsTable.COLUMN_NAME + "=?",
                new String[]{name},
                null);
        cursor.moveToFirst();

        long time = new Date().getTime();
        if (cursor.getCount() == 0) {
            // insert it
            contentResolver.insert(ItemsTable.CONTENT_URI, toContentValues());
            new Sale(time, SalesTable.SALE_TYPE_BUY, id, quantity, quantity * price)
                    .saveSale(contentResolver);
        } else {
            // update it
            Item storedItem = Item.fromCursor(cursor);
            int boughtQuantity = this.quantity - storedItem.quantity;

            if (boughtQuantity == 0) {
                throw new Exception("buying nothing");
            } else if (boughtQuantity < 0) {
                throw new Exception("newItem's quantity is less than storedItem quantity");
            } else {
                contentResolver.update(ItemsTable.CONTENT_URI,
                        toContentValues(), ItemsTable.COLUMN_NAME + "=?", new String[]{name});
                new Sale(time, SalesTable.SALE_TYPE_BUY, storedItem.id, boughtQuantity, boughtQuantity * price)
                        .saveSale(contentResolver);
            }
        }

        cursor.close();
    }
}
