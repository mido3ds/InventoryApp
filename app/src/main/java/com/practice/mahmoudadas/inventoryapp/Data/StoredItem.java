package com.practice.mahmoudadas.inventoryapp.Data;


import android.content.ContentValues;
import android.database.Cursor;

public class StoredItem {
    private int id = 0;
    private String name = "";
    private String supplierName = "";
    private int quantity = 0;
    private float price = 0;
    private int imgResourceId = 0;

    public StoredItem(int id, String name, String supplierName, int quantity, float price, int imgResourceId) {
        setId(id);
        setName(name);
        setSupplierName(supplierName);
        setQuantity(quantity);
        setPrice(price);
        setImgResourceId(imgResourceId);
    }

    public StoredItem(String name, String supplierName, int quantity, float price, int imgResourceId) {
        setName(name);
        setSupplierName(supplierName);
        setQuantity(quantity);
        setPrice(price);
        setImgResourceId(imgResourceId);
    }

    public StoredItem(Cursor cursor) {
        checkIsValidCursor(cursor);

        // TODO: get cursor content
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

    public static boolean isValildName(String name) {
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
        if (!isValildName(name)) {
            throw new IllegalArgumentException("name cant be empty");
        }
        this.name = name;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        if (!isValildName(supplierName)) {
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
        return "StoredItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        // TODO
        return values;
    }
}
