package com.almogdad.pos;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.almogdad.pos.adapter.CartAdapter;
import com.almogdad.pos.adapter.ProductAdapter;
import com.almogdad.pos.constants.AppConstant;
import com.almogdad.pos.constants.PrintMaster;
import com.almogdad.pos.model.Cart;
import com.almogdad.pos.model.Invoice;
import com.almogdad.pos.model.Order;
import com.almogdad.pos.model.Product;
import com.almogdad.pos.new_product.AddPropertyActivity;
import com.almogdad.pos.reports.ReportsActivity;
import com.almogdad.pos.view_model.CartViewModel;
import com.almogdad.pos.view_model.InvoiceViewModel;
import com.almogdad.pos.view_model.OrderViewModel;
import com.almogdad.pos.view_model.ProductViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Context mContext;
    private ProductViewModel productViewModel;
    private CartViewModel cartViewModel;
    private OrderViewModel orderViewModel;
    private InvoiceViewModel invoiceViewModel;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCart;
    private TextView totalTextView;
    private TextView qtyTextView;
    private TextView discountTextView;
    private TextView taxTextView;
    private TextView subTotalTextView;
    private SwitchMaterial taxSwitch;
    private ProductAdapter productAdapter;
    private CartAdapter cartAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

    }

    private void initialization() {
        mContext = this;

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewCart = findViewById(R.id.recycler_view_cart);

        totalTextView = findViewById(R.id.total);
        taxTextView = findViewById(R.id.tax);
        subTotalTextView = findViewById(R.id.subtotal);
        qtyTextView = findViewById(R.id.total_qty);
        discountTextView = findViewById(R.id.discount);

        taxSwitch = findViewById(R.id.taxSwitch);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter(cartViewModel);
        cartAdapter = new CartAdapter(cartViewModel, this);

        recyclerView.setAdapter(productAdapter);
        recyclerViewCart.setAdapter(cartAdapter);

        observeViewModels();

        taxSwitch.setOnCheckedChangeListener(new SwitchMaterial.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    taxSwitch.setText(getString(R.string.inclusive));
                    AppConstant.isExclusive = false;
                } else {
                    taxSwitch.setText(getString(R.string.exclusive));
                    AppConstant.isExclusive = true;

                }
                double total = cartViewModel.calculateTotal();

                if(total > 0) {
                    cartViewModel.updateTotal();
                }
            }


        });
    }

    private void observeViewModels() {
        orderViewModel.getAllOrders().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
            }
        });


        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.setProducts(products);
            }
        });

        cartViewModel.getSelectedProducts().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> selectedProducts) {
                cartAdapter.setCarts(selectedProducts);
            }
        });

        cartViewModel.getTotal().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double total) {
                totalTextView.setText(total.toString());
            }
        });

        cartViewModel.getTotalQTY().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer qty) {
                qtyTextView.setText(qty.toString());
            }
        });

        cartViewModel.getTotalDiscount().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double discount) {
                discountTextView.setText(discount.toString());
            }
        });

        cartViewModel.getTax().observe(this, new Observer<Double>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Double tax) {
                taxTextView.setText(String.valueOf(AppConstant.round(tax, AppConstant.DecimalPoints)));
            }
        });

        cartViewModel.getSubTotal().observe(this, new Observer<Double>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Double subTotal) {
                subTotalTextView.setText(String.valueOf(AppConstant.round(subTotal, AppConstant.DecimalPoints)));
            }
        });
    }


    public void addNewProduct(View view) {

        // add Product with Dialog

/*        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_add_property_popup, null);
        dialogBuilder.setView(dialogView);

        EditText productNameEditText = dialogView.findViewById(R.id.product_name);
        EditText productPriceEditText = dialogView.findViewById(R.id.product_price);

        dialogBuilder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String productName = productNameEditText.getText().toString();
                String productPrice = productPriceEditText.getText().toString();

                if(productName.trim().isEmpty() || productPrice.trim().isEmpty()){
                    Toast.makeText(mContext, "Please Insert The Title and the Price", Toast.LENGTH_SHORT).show();
                }else {
                    saveProduct(productName, productPrice);
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();*/

        // add Product with Activity

        Intent intent = new Intent(MainActivity.this, AddPropertyActivity.class);
        addPropertyLauncher.launch(intent);
    }

    private void saveProduct(String name, String price) {
        Product product = new Product(name, Double.parseDouble(price));
        productViewModel.insert(product);

        Toast.makeText(mContext, "Product Save", Toast.LENGTH_SHORT).show();
    }

    public void clearCart(View view) {
        cartViewModel.clearCart();

    }

    public void payment(View view) {

        double total = cartViewModel.calculateTotal();

        if(total > 0) {

            Invoice newInvoice = new Invoice(total, 0.0);
            LiveData<Long> invoiceIdLiveData = invoiceViewModel.insert(newInvoice);

            saveOrderDetails(invoiceIdLiveData);
        }


    }

    private void saveOrderDetails(LiveData<Long> invoiceIdLiveData) {

        invoiceIdLiveData.observe(MainActivity.this, new Observer<Long>() {
            @Override
            public void onChanged(Long invoiceId) {

                if (invoiceId != null) {
                    List<Order> orders = cartViewModel.createOrdersFromCart(invoiceId);


                    for(Order order: orders){
                        orderViewModel.insert(order);
                    }

//                     Call print Function

                      printInvoice(
                            orders,
                            invoiceId,
                            totalTextView.getText().toString(),
                            subTotalTextView.getText().toString(),
                            qtyTextView.getText().toString(),
                            discountTextView.getText().toString(),
                            taxTextView.getText().toString()
                            );

                    cartViewModel.clearCart();
                }
            }
        });

//        cartViewModel.clearCart();
    }


    public void reports(View view) {
        startActivity(new Intent(MainActivity.this, ReportsActivity.class));
    }


    // Disable the Back-Button
    @Override
    public void onBackPressed() {

    }

    private void printInvoice(List<Order> orders, long invoiceID, String total,
                              String subTotal, String qty, String discount, String tax){
        StringBuilder invoice = new StringBuilder();


        PrintMaster.printBitmap("");
        PrintMaster.printText("invoiceID: \t"+invoiceID, 16);
        PrintMaster.printText("Date: \t"+AppConstant.dateFormat.format(new Date()), 16);

        Log.d("printInvoice", "printInvoice: "+ AppConstant.dateFormat.format(new Date()));

        for(Order order: orders){
            invoice.append("Product:  \t").append(order.getProductId()).append("\n");
            invoice.append("Quantity:  \t").append(order.getQuantity()).append("\n");
            invoice.append("Discount:  \t").append(order.getDiscount()).append("\n");
            invoice.append("Total:  \t").append(order.getTotal()).append("\n");
        }

        PrintMaster.printText(invoice.toString(), 14);

//        Log.d("printInvoice", invoice.toString());
//        Log.d("printInvoice", "subTotal:  \t"+ subTotal);
//        Log.d("printInvoice", "Total Quantity:  \t"+ qty);
//        Log.d("printInvoice", "Total discount:  \t"+ discount);
//        Log.d("printInvoice", "Vat (5%):  \t"+ tax);
//        Log.d("printInvoice", "Total:  \t"+ total);


        PrintMaster.printText("subTotal:  \t"+ subTotal, 16);
        PrintMaster.printText("Total Quantity:  \t"+ qty, 16);
        PrintMaster.printText("discount:  \t"+ discount, 16);
        PrintMaster.printText("Vat (5%):  \t"+ tax, 16);
        PrintMaster.printText("Total:  \t"+ total, 16);
    }


    ActivityResultLauncher<Intent> addPropertyLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();

                        String name = data.getStringExtra(AddPropertyActivity.EXTRA_NAME);
                        String price = data.getStringExtra(AddPropertyActivity.EXTRA_PRICE);
                        saveProduct(name, price);

                    } else {
                        Toast.makeText(mContext, "Product Not Save", Toast.LENGTH_SHORT).show();

                    }
                }
            });
}