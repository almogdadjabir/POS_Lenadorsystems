package com.almogdad.pos.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.almogdad.pos.model.Invoice;
import com.almogdad.pos.repository.InvoiceRepository;

import java.util.List;

public class InvoiceViewModel extends AndroidViewModel {

    private InvoiceRepository repository;
    private LiveData<List<Invoice>> allInvoices;

    public InvoiceViewModel(@NonNull Application application) {
        super(application);
        repository = new InvoiceRepository(application);
        allInvoices = repository.getAllInvoices();
    }

    public LiveData<Long> insert(Invoice invoice) {
        return repository.insert(invoice);
    }

    public void update(Invoice invoice){
        repository.update(invoice);
    }

    public void delete(Invoice invoice){
        repository.delete(invoice);
    }

    public void deleteAllInvoice(){
        repository.deleteAllInvoices();
    }

    public LiveData<List<Invoice>> getAllInvoices() {
        return allInvoices;
    }

}
