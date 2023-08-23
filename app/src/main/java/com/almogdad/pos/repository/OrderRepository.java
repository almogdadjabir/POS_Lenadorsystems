package com.almogdad.pos.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.almogdad.pos.dao.OrderDao;
import com.almogdad.pos.data.ProductDatabase;
import com.almogdad.pos.model.Order;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {
    private OrderDao orderDao;
    private LiveData<List<Order>> allOrders;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public OrderRepository(Application application){
        ProductDatabase productDatabase = ProductDatabase.getInstance(application);
        orderDao = productDatabase.orderDao();
        allOrders = orderDao.getAllOrders();
    }

    public void insert(Order order) {
        executorService.execute(() -> {
            orderDao.insert(order);
        });
    }

    public void update(Order order) {
        executorService.execute(() -> {
            orderDao.update(order);
        });
    }

    public void delete(Order order) {
        executorService.execute(() -> {
            orderDao.delete(order);
        });
    }

    public void deleteAllOrders() {
        executorService.execute(() -> {
            orderDao.deleteAllOrders();
        });
    }



    public LiveData<List<Order>> getAllOrders() {
        return allOrders;
    }



    public LiveData<List<Order>> getOrdersByInvoiceId(long invoiceId) {

        return orderDao.getOrdersByInvoiceIdLiveData(invoiceId);
    }



}
