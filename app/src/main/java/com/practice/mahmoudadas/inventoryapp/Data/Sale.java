package com.practice.mahmoudadas.inventoryapp.Data;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.practice.mahmoudadas.inventoryapp.Data.InventoryContract.SalesTable;

public class Sale {
    private int id = 0;
    private long date = 0;
    private int type = 0;
    private int itemId = 0;
    private int quantity = 0;
    private float totalPrice = 0;

    public Sale(int id, long date, int type, int itemId, int quantity, float totalPrice) {
        setId(id);
        setDate(date);
        setType(type);
        setItemId(itemId);
        setQuantity(quantity);
        setTotalPrice(totalPrice);
    }

    public Sale(long date, int type, int itemId, int quantity, float totalPrice) {
        setDate(date);
        setType(type);
        setItemId(itemId);
        setQuantity(quantity);
        setTotalPrice(totalPrice);
    }

    public Sale() {
        // empty one
    }

    public static Sale fromCursor(Cursor cursor) {
        Item.checkIsValidCursor(cursor);
        int validColumnsInCursor = 0;
        Sale sale = new Sale();

        int idIndex = cursor.getColumnIndex(SalesTable._ID);
        if (idIndex != -1) {
            validColumnsInCursor++;
            sale.setId(cursor.getInt(idIndex));
        }

        int dateIndex = cursor.getColumnIndex(SalesTable.COLUMN_DATE);
        if (dateIndex != -1) {
            validColumnsInCursor++;
            sale.setDate(cursor.getLong(dateIndex));
        }

        int typeIndex = cursor.getColumnIndex(SalesTable.COLUMN_SALE_TYPE);
        if (typeIndex != -1) {
            validColumnsInCursor++;
            sale.setType(cursor.getInt(typeIndex));
        }

        int itemIdIndex = cursor.getColumnIndex(SalesTable.COLUMN_ITEM_ID);
        if (itemIdIndex != -1) {
            validColumnsInCursor++;
            sale.setItemId(cursor.getInt(itemIdIndex));
        }

        int quantityIndex = cursor.getColumnIndex(SalesTable.COLUMN_QUANTITY);
        if (quantityIndex != -1) {
            validColumnsInCursor++;
            sale.setQuantity(cursor.getInt(quantityIndex));
        }

        int totalPriceIndex = cursor.getColumnIndex(SalesTable.COLUMN_TOTAL_PRICE);
        if (totalPriceIndex != -1) {
            validColumnsInCursor++;
            sale.setTotalPrice(cursor.getFloat(totalPriceIndex));
        }

        if (validColumnsInCursor == 0) {
            throw new IllegalArgumentException("cursor doesn't have necessary columns");
        }

        return sale;
    }

    public static boolean isValidDate(long date) {
        return date > 0;
    }

    private static boolean isValidType(int type) {
        return type == SalesTable.SALE_TYPE_BUY
                || type == SalesTable.SALE_TYPE_SELL;
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
        if (!Item.isValidId(itemId)) {
            throw new IllegalArgumentException("invalid id");
        }
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (!Item.isValidQuantity(quantity)) {
            throw new IllegalArgumentException("invalid quantity");
        }
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        if (!Item.isValidPrice(totalPrice)) {
            throw new IllegalArgumentException("invalid total price");
        }
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Sale{" +
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

        values.put(SalesTable._ID, id);
        values.put(SalesTable.COLUMN_DATE, date);
        values.put(SalesTable.COLUMN_SALE_TYPE, type);
        values.put(SalesTable.COLUMN_ITEM_ID, itemId);
        values.put(SalesTable.COLUMN_QUANTITY, quantity);
        values.put(SalesTable.COLUMN_TOTAL_PRICE, totalPrice);

        return values;
    }

    public void saveSale(ContentResolver contentResolver) throws Exception {
        if (contentResolver == null) {
            throw new IllegalArgumentException("contentResolver is null");
        }

        if (contentResolver.insert(SalesTable.CONTENT_URI, this.toContentValues()) == null) {
            throw new Exception("couldn't save sale info to db " + this);
        }
    }
}
