package com.almogdad.pos.reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.almogdad.pos.R;
import com.almogdad.pos.model.Invoice;
import com.almogdad.pos.reports.adapter.ReportAdapter;
import com.almogdad.pos.view_model.InvoiceViewModel;
import com.almogdad.pos.view_model.OrderViewModel;
import com.almogdad.pos.view_model.ProductViewModel;

import java.util.List;

public class ReportsActivity extends AppCompatActivity {

    private OrderViewModel orderViewModel;
    private ProductViewModel productViewModel;
    private InvoiceViewModel invoiceViewModel;

    private RecyclerView recyclerView;
    private ReportAdapter reportAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        initialization();

    }

    private void initialization() {
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);


        recyclerView = findViewById(R.id.recycler_view);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));


        reportAdapter = new ReportAdapter(orderViewModel, productViewModel,this, this);

        recyclerView.setAdapter(reportAdapter);

        observeViewModels();

    }

    private void observeViewModels() {
        invoiceViewModel.getAllInvoices().observe(this, new Observer<List<Invoice>>() {
            @Override
            public void onChanged(List<Invoice> invoices) {
                reportAdapter.setInvoices(invoices);
            }
        });
    }



        public void back(View view) {
        finish();
    }
}