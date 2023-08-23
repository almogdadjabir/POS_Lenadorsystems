package com.almogdad.pos.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.almogdad.pos.R;
import com.almogdad.pos.constants.AppConstant;
import com.almogdad.pos.model.Cart;
import com.almogdad.pos.view_model.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private List<Cart> carts = new ArrayList<>();
    private CartViewModel cartViewModel;
    private Context mContext;


    public CartAdapter(CartViewModel cartViewModel, Context mContext) {
        this.cartViewModel = cartViewModel;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Cart curentCart = carts.get(position);
        holder.product_name.setText(curentCart.getProduct().getName());
        holder.product_price.setText(String.valueOf(curentCart.getTotal()));
        holder.quantity.setText(String.valueOf(curentCart.getQty()));

        holder.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curentCart.setQty(curentCart.getQty() + 1);
                curentCart.setTotal(AppConstant.getTotal(curentCart.getProduct().getPrice() , curentCart.getQty()));
                cartViewModel.updateSelectedProduct(curentCart);
            }
        });

        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curentCart.getQty() != 1) {
                    curentCart.setQty(curentCart.getQty() - 1);
                    curentCart.setTotal(AppConstant.getTotal(curentCart.getProduct().getPrice() , curentCart.getQty()));
                    cartViewModel.updateSelectedProduct(curentCart);
                }else {
                    cartViewModel.removeSelectedProduct(curentCart);
                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscountPopup(curentCart);

            }
        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public void setCarts(List<Cart> carts){
         this.carts = carts;

         notifyDataSetChanged();
    }

    class CartHolder extends RecyclerView.ViewHolder{
        private TextView product_name;
        private TextView product_price;
        private TextView quantity;
        private ImageView addProduct;
        private ImageView removeProduct;


        public CartHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.quantity);
            addProduct = itemView.findViewById(R.id.addProduct);
            removeProduct = itemView.findViewById(R.id.removeProduct);


        }
    }


    private void showDiscountPopup(Cart cart) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_discount_popup, null);
        dialogBuilder.setView(dialogView);

        EditText discountEditText = dialogView.findViewById(R.id.discountEditText);

        dialogBuilder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String discountValueStr = discountEditText.getText().toString();
                if (!TextUtils.isEmpty(discountValueStr)) {
                    double discountValue = Double.parseDouble(discountValueStr);
                    if(discountValue <= AppConstant.getTotal(cart.getProduct().getPrice() , cart.getQty())) {
                        cart.setTotal(AppConstant.getTotal(cart.getProduct().getPrice(), cart.getQty()) - discountValue);
                        cart.setDiscount(discountValue);
                        cartViewModel.updateSelectedProduct(cart);
                    }else{
                        Toast.makeText(mContext, "Sorry! Discount cannot be more then"+AppConstant.getTotal(cart.getProduct().getPrice() , cart.getQty()), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}

