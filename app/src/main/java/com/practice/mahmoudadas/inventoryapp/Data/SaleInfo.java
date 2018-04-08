package com.practice.mahmoudadas.inventoryapp.Data;


import android.content.ContentValues;
import android.database.Cursor;

public class SaleInfo {
    private int id = 0;
    private long date = 0;
    private int type = 0;
    private int itemId = 0;
    private int quantity = 0;
    private float totalPrice = 0;

    public SaleInfo(int id, long date, int type, int itemId, int quantity, float totalPrice) {
        setId(id);
        setDate(date);
        setType(type);
        setItemId(itemId);
        setQuantity(quantity);
        setTotalPrice(totalPrice);
    }

    public SaleInfo(long date, int type, int itemId, int quantity, float totalPrice) {
        setDate(date);
        setType(type);
        setItemId(itemId);
        setQuantity(quantity);
        setTotalPrice(totalPrice);
    }

    public SaleInfo(Cursor cursor) {
        StoredItem.checkIsValidCursor(cursor);

        // TODO: extract cursor
    }

    public static boolean isValidDate(long date) {
        return date > 0;
    }

    private static boolean isValidType(int type) {
        return false; // TODO
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException("date must be a positive number of seconds");
        }
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if (!isValidType(type)) {
            throw new IllegalArgumentException("invalid sale type");
        }
        this.type = type;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        if (!StoredItem.isValidId(itemId)) {
            throw new IllegalArgumentException("invalid id");
        }
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (!StoredItem.isValidQuantity(quantity)) {
            throw new IllegalArgumentException("invalid quantity");
        }
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        if (!StoredItem.isValidPrice(totalPrice)) {
            throw new IllegalArgumentException("invalid total price");
        }
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "SaleInfo{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        // TODO
        return values;
    }
}
