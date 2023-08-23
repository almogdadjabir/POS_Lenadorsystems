package com.almogdad.pos.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.almogdad.pos.constants.AppConstant;
import com.almogdad.pos.model.Cart;
import com.almogdad.pos.model.Order;
import com.almogdad.pos.model.Product;
import com.almogdad.pos.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository repository;
    private LiveData<List<Product>> allProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        allProducts = repository.getAllProducts();
    }

    public void insert(Product product){
        repository.insert(product);
    }

    public void update(Product product){
        repository.update(product);
    }

    public void delete(Product product){
        repository.delete(product);
    }

    public void deleteAllProducts(){
        repository.deleteAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public LiveData<Product> getProductById(long productID) {
        return repository.getProductById(productID);
    }

//    public Product getProductById(long productID) {
//        Product product = _getProductById(productID).getValue();
//
//        if (product == null) {
//            return new Product("test", 0.0);
//        }
//
//        return product;
//    }

}
