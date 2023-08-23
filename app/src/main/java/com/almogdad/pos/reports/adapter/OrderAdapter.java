package com.almogdad.pos.reports.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.almogdad.pos.R;
import com.almogdad.pos.model.Invoice;
import com.almogdad.pos.model.Order;
import com.almogdad.pos.model.Product;
import com.almogdad.pos.view_model.OrderViewModel;
import com.almogdad.pos.view_model.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ProductHolder> {
    private List<Order> orders = new ArrayList<>();

    public OrderAdapter(ProductViewModel productViewModel, LifecycleOwner lifecycleOwner) {
        this.productViewModel = productViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    private ProductViewModel productViewModel;
    private LifecycleOwner lifecycleOwner;




    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new ProductHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Order curentOrder = orders.get(position);
        holder.price.setText(String.valueOf(curentOrder.getTotal()));
        holder.qty.setText(String.valueOf(curentOrder.getQuantity()));

        if(curentOrder.getDiscount() != 0.0){
            holder.discount.setText("-"+curentOrder.getDiscount());
            holder.discount.setTextColor(Color.RED);
        }

        productViewModel.getProductById(curentOrder.getProductId()).observe(lifecycleOwner, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product == null) {
                } else {
                    holder.name.setText(product.getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<Order> orders){
         this.orders = orders;

         notifyDataSetChanged();
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView price;
        private TextView qty;
        private TextView discount;


        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            qty = itemView.findViewById(R.id.product_qty);
            discount = itemView.findViewById(R.id.product_discount);


        }
    }
}

