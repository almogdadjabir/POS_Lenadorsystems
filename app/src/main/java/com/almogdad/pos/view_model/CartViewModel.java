package com.almogdad.pos.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.almogdad.pos.constants.AppConstant;
import com.almogdad.pos.model.Cart;
import com.almogdad.pos.model.Order;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<List<Cart>> cartList = new MutableLiveData<>();

    public LiveData<List<Cart>> getSelectedProducts() {
        return cartList;
    }

    public void addProductToCart(Cart cart) {
        List<Cart> updatedList = cartList.getValue();
        if (updatedList == null) {
            updatedList = new ArrayList<>();
            updatedList.add(cart);
        } else {
            // Check if the product is already in the cart
            boolean productExists = false;
            for (Cart existingCart : updatedList) {
                if (existingCart.getProduct().getId() == cart.getProduct().getId()) {
                    existingCart.setQty(existingCart.getQty() + cart.getQty());
                    existingCart.setTotal(AppConstant.getTotal(existingCart.getProduct().getPrice(), existingCart.getQty()));
                    productExists = true;
                    updateTotal();
                    break;
                }
            }
            if (!productExists) {
                updatedList.add(cart);
            }
        }
        cartList.setValue(updatedList);
        updateTotal();
    }

    public void removeSelectedProduct(Cart cart) {
        List<Cart> updatedList = cartList.getValue();
        if (updatedList != null) {
            updatedList.remove(cart);
            cartList.setValue(updatedList);
            updateTotal();
        }
    }

    public LiveData<Double> getTotal() {
        LiveData<List<Cart>> selectedProductsLiveData = cartList;

        return Transformations.map(selectedProductsLiveData, carts -> {
            double total = 0;

            for (Cart cart : carts) {
                total += cart.getTotal();
            }

            double tax = AppConstant.taxCalculator(total, true);

            return AppConstant.isExclusive ? AppConstant.round(total + tax, AppConstant.DecimalPoints)
                    : AppConstant.round(total, AppConstant.DecimalPoints);
        });
    }


    public LiveData<Double> getTotalDiscount() {
        LiveData<List<Cart>> selectedProductsLiveData = cartList;

        return Transformations.map(selectedProductsLiveData, carts -> {
            double totalDiscount = 0;

            for (Cart cart : carts) {
                totalDiscount += cart.getDiscount();
            }

            return totalDiscount;
        });
    }


    public LiveData<Integer> getTotalQTY() {
        LiveData<List<Cart>> selectedProductsLiveData = cartList;

        return Transformations.map(selectedProductsLiveData, carts -> {
            int totalQty = 0;

            for (Cart cart : carts) {
                totalQty += cart.getQty();
            }

            return totalQty;
        });
    }


    public LiveData<Double> getSubTotal() {
        LiveData<List<Cart>> selectedProductsLiveData = cartList;

        return Transformations.map(selectedProductsLiveData, carts -> {
            double total = 0;

            for (Cart cart : carts) {
                total += cart.getTotal();
            }

            double tax = AppConstant.taxCalculator(total, true);

            if (!AppConstant.isExclusive) {
                return AppConstant.round(total - tax, AppConstant.DecimalPoints);
            } else {
                return AppConstant.round(total, AppConstant.DecimalPoints);
            }
        });
    }


    public LiveData<Double> getTax() {
        LiveData<List<Cart>> selectedProductsLiveData = cartList;

        return Transformations.map(selectedProductsLiveData, carts -> {
            double total = 0;

            for (Cart cart : carts) {
                total += cart.getTotal();
            }

            return AppConstant.taxCalculator(total, AppConstant.isExclusive);
        });
    }


    public void updateTotal() {
        cartList.setValue(cartList.getValue());
    }

    public void updateSelectedProduct(Cart cart) {
        List<Cart> updatedList = cartList.getValue();

        if (updatedList != null) {
            for (int i = 0; i < updatedList.size(); i++) {
                Cart updatedCart = updatedList.get(i);

                if (updatedCart.getProduct().getId() == cart.getProduct().getId()) {
                    updatedCart.setQty(cart.getQty());
                    updatedCart.setDiscount(cart.getDiscount());
                    updatedList.set(i, updatedCart);

                    cartList.setValue(updatedList);
                    updateTotal();
                    break;
                }
            }
        }
    }


    public void clearCart() {
        cartList.setValue(new ArrayList<>());
    }

    public List<Order> createOrdersFromCart(long invoice) {
        List<Order> orders = new ArrayList<>();
        List<Cart> cartItems = cartList.getValue();



        if (cartItems != null) {

            for (Cart cartItem : cartItems) {

                Order order = new Order(
                        cartItem.getProduct().getId(),
                        invoice,
                        cartItem.getQty(),
                        cartItem.getDiscount(),
                        cartItem.getTotal()
                );
                orders.add(order);
            }
        }
        return orders;
    }

    public double calculateTotal() {
        List<Cart> carts = cartList.getValue();
        if (carts == null) {
            return 0.0;
        }

        double total = 0;
        for (Cart cart : carts) {
            total += cart.getTotal();
        }

        return AppConstant.calculateTotalWithTax(total);
    }



}