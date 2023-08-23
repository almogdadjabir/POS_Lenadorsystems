package com.almogdad.pos.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


public class Cart {

    public Cart(Product product, int qty, double tax, double total, double subTotal, double discount) {
        this.product = product;
        this.qty = qty;
        this.tax = tax;
        this.total = total;
        this.subTotal = subTotal;
        this.discount = discount;
    }



    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    private Product product;

    private int qty;

    private double tax;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    private double total;
    private double subTotal;
    private double discount;

}
