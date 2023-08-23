package com.almogdad.pos.new_product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.almogdad.pos.R;

import java.util.Objects;

public class AddPropertyActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPrice;

    public static final String EXTRA_NAME =
            "com.almogdad.pos.new_product.EXTRA_NAME";


    public static final String EXTRA_PRICE =
            "com.almogdad.pos.new_product.EXTRA_PRICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        editTextName = findViewById(R.id.product_name);
        editTextPrice = findViewById(R.id.product_price);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.baseline_close);
        setTitle("Add Product ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_product_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_product) {
            saveProduct();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveProduct() {
        String productName = editTextName.getText().toString();
        String productPrice = editTextPrice.getText().toString();

        if(productName.trim().isEmpty() || productPrice.trim().isEmpty()){
            Toast.makeText(this, "Please Insert The Title and the Price", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, productName);
        data.putExtra(EXTRA_PRICE, productPrice);
        setResult(RESULT_OK, data);
        finish();
    }

}