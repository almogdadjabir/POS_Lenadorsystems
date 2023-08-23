package com.almogdad.pos.reports.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almogdad.pos.R;
import com.almogdad.pos.model.Invoice;
import com.almogdad.pos.model.Order;
import com.almogdad.pos.view_model.CartViewModel;
import com.almogdad.pos.view_model.OrderViewModel;
import com.almogdad.pos.view_model.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {
    private List<Invoice> invoices = new ArrayList<>();
    private Context mContext;
    private OrderViewModel orderViewModel;
    private ProductViewModel productViewModel;

    private LifecycleOwner lifecycleOwner;


    public ReportAdapter(OrderViewModel orderViewModel, ProductViewModel productViewModel, Context mContext, LifecycleOwner lifecycleOwner) {
        this.orderViewModel = orderViewModel;
        this.productViewModel = productViewModel;
        this.mContext = mContext;
        this.lifecycleOwner = lifecycleOwner;
    }


    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_item, parent, false);
        return new ReportHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {
        Invoice curentInvoice = invoices.get(position);
        holder.name.setText(String.valueOf(curentInvoice.getId()));
        holder.price.setText(String.valueOf(curentInvoice.getTotal()));


        OrderAdapter orderAdapter = new OrderAdapter(productViewModel, lifecycleOwner);
        holder.recyclerView.setAdapter(orderAdapter);


        orderViewModel.getOrdersByInvoiceIdLiveData(curentInvoice.getId()).observe(lifecycleOwner, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                Log.d("orders_orders", "" + orders.size());
                if(orders.size() == 0){
                    holder.detailsHeader.setVisibility(View.GONE);
                }
                orderAdapter.setOrders(orders);
            }
        });
    }


    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public void setInvoices(List<Invoice> invoices){
         this.invoices = invoices;

         notifyDataSetChanged();
    }

    class ReportHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView price;
        private RecyclerView recyclerView;
        private LinearLayout detailsHeader;


        public ReportHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.invoice_id);
            price = itemView.findViewById(R.id.invoice_total);
            detailsHeader = itemView.findViewById(R.id.details_header);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));



        }
    }
}

