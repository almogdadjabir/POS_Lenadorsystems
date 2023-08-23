package com.almogdad.pos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.almogdad.pos.model.Invoice;

import java.util.List;

@Dao
public interface InvoiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Invoice invoice);

    @Update
    void update(Invoice invoice);

    @Delete
    void delete(Invoice invoice);


    @Query("Delete FROM invoice_table")
    void deleteAllInvoice();

    @Query("SELECT * FROM invoice_table")
    LiveData<List<Invoice>> getAllInvoice();
}
