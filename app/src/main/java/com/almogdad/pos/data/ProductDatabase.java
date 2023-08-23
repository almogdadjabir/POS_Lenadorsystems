package com.almogdad.pos.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.almogdad.pos.dao.OrderDao;
import com.almogdad.pos.dao.ProductDao;
import com.almogdad.pos.dao.InvoiceDao;
import com.almogdad.pos.model.Invoice;
import com.almogdad.pos.model.Order;
import com.almogdad.pos.model.Product;

@Database(entities = {Product.class, Order.class, Invoice.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    private static ProductDatabase instance;

    public abstract ProductDao productDao();
    public abstract OrderDao orderDao();
    public abstract InvoiceDao invoiceDao();

    public static synchronized ProductDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                     ProductDatabase.class, "product_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return  instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new ProductDbAsyncTask(instance).execute();
            super.onCreate(db);
        }
    };

    private static class ProductDbAsyncTask extends AsyncTask<Void, Void,Void>{
        private ProductDao productDao;

        private ProductDbAsyncTask(ProductDatabase db){
            productDao = db.productDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.insert(new Product("Book1", 35));
            productDao.insert(new Product("Book2", 15.5));
            productDao.insert(new Product("Book3", 25.0));
            productDao.insert(new Product("Book4", 50));
            productDao.insert(new Product("Book5", 133));
            return null;
        }
    }
}
