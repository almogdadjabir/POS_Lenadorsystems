package com.almogdad.pos.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.almogdad.pos.model.Order;
import com.almogdad.pos.repository.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository repository;
    private LiveData<List<Order>> allOrders;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        repository = new OrderRepository(application);
        allOrders = repository.getAllOrders();
    }

    public void insert(Order order){
        repository.insert(order);
    }

    public void update(Order order){
        repository.update(order);
    }

    public void delete(Order order){
        repository.delete(order);
    }

    public void deleteAllOrders(){
        repository.deleteAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return allOrders;
    }

    public LiveData<List<Order>> getOrdersByInvoiceIdLiveData(long invoiceId) {
        return repository.getOrdersByInvoiceId(invoiceId);
    }

}
