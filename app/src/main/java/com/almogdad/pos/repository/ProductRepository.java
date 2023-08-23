package com.almogdad.pos.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.almogdad.pos.dao.ProductDao;
import com.almogdad.pos.data.ProductDatabase;
import com.almogdad.pos.model.Invoice;
import com.almogdad.pos.model.Order;
import com.almogdad.pos.model.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public ProductRepository(Application application){
        ProductDatabase productDatabase = ProductDatabase.getInstance(application);
        productDao = productDatabase.productDao();
        allProducts = productDao.getAllProducts();
    }

    public void insert(Product product){
        executorService.execute(() -> {
            productDao.insert(product);
        });
    }

    public void update(Product product){
        executorService.execute(() -> {
            productDao.update(product);
        });
    }

    public void delete(Product product){
        executorService.execute(() -> {
            productDao.delete(product);
        });
    }

    public void deleteAllProducts(){
        executorService.execute(() -> {
            productDao.deleteAllProducts();
        });
    }


    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public LiveData<Product> getProductById(long productId) {
        return productDao.getProductById(productId);
    }

}
