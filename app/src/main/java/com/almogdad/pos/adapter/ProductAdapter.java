package com.almogdad.pos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almogdad.pos.R;
import com.almogdad.pos.constants.AppConstant;
import com.almogdad.pos.model.Cart;
import com.almogdad.pos.model.Product;
import com.almogdad.pos.view_model.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private List<Product> products = new ArrayList<>();
    private CartViewModel viewModel;

    public ProductAdapter(CartViewModel viewModel) {
        this.viewModel = viewModel;
    }


    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product curentProduct = products.get(position);
        holder.name.setText(curentProduct.getName());
        holder.price.setText(String.valueOf(curentProduct.getPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products){
         this.products = products;

         notifyDataSetChanged();
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView price;


        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Product clickedProduct = products.get(position);
                        viewModel.addProductToCart(
                                new Cart(
                                        clickedProduct, 1, 0.0,
                                        AppConstant.getTotal(clickedProduct.getPrice(), 1),
                                        AppConstant.getTotal(clickedProduct.getPrice(), 1),
                                        0.0
                                        )
                        );
                    }
                }
            });

        }
    }
}

