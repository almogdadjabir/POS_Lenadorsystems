package com.almogdad.pos.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "invoice_table"
)
public class Invoice {

    @PrimaryKey(autoGenerate = true)
    private long id;


    public Invoice(double total, double totalDiscount, double tax) {
        this.total = total;
        this.totalDiscount = totalDiscount;
        this.tax = tax;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }



    public double getTotal() {
        return total;
    }

    public double getTax() {
        return tax;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    private double total;
    private double tax;

    private double totalDiscount;
}