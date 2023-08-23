package com.almogdad.pos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.almogdad.pos.model.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

    @Query("Delete FROM order_table")
    void deleteAllOrders();

    @Query("SELECT * FROM order_table")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM order_table WHERE invoiceID = :invoiceId")
    LiveData<List<Order>> getOrdersByInvoiceIdLiveData(long invoiceId);

}
