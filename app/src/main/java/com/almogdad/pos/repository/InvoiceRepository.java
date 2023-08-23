package com.almogdad.pos.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.almogdad.pos.dao.InvoiceDao;
import com.almogdad.pos.data.ProductDatabase;
import com.almogdad.pos.model.Invoice;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InvoiceRepository {
    private InvoiceDao invoiceDao;
    private LiveData<List<Invoice>> allInvoices;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public InvoiceRepository(Application application){
        ProductDatabase productDatabase = ProductDatabase.getInstance(application);
        invoiceDao = productDatabase.invoiceDao();
        allInvoices = invoiceDao.getAllInvoice();
    }


    public void update(Invoice invoice) {
        executorService.execute(() -> {
            invoiceDao.update(invoice);
        });
    }

    public void delete(Invoice invoice) {
        executorService.execute(() -> {
            invoiceDao.delete(invoice);
        });
    }


    public void deleteAllInvoices() {
        executorService.execute(() -> {
            invoiceDao.deleteAllInvoice();
        });
    }

    public LiveData<List<Invoice>> getAllInvoices() {
        return allInvoices;
    }



    public LiveData<Long> insert(Invoice invoice) {
        MutableLiveData<Long> insertedInvoiceId = new MutableLiveData<>();

        Future<Long> futureResult = executorService.submit(() -> {
            return invoiceDao.insert(invoice);
        });

        executorService.execute(() -> {
            try {
                Long id = futureResult.get();
                insertedInvoiceId.postValue(id);
            } catch (Exception e) {
                // Handle exceptions here
            }
        });

        return insertedInvoiceId;
    }

}
