package com.almogdad.pos.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "order_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Product.class,
                        parentColumns = "id",
                        childColumns = "productId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Invoice.class,
                        parentColumns = "id",
                        childColumns = "invoiceID",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Order {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long productId;
    private long invoiceID;

    public void setInvoiceID(long invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Order(long productId, long invoiceID, int quantity, double discount, double total) {
        this.productId = productId;
        this.invoiceID = invoiceID;
        this.quantity = quantity;
        this.discount = discount;
        this.total = total;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public long getProductId() {
        return productId;
    }
    public long getInvoiceID() {
        return invoiceID;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private int quantity;

    private double discount;
    private double total;
}
