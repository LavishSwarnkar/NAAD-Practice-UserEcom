package com.lavish.android.userecom;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lavish.android.userecom.adapters.ProductsAdapter;
import com.lavish.android.userecom.databinding.ActivityCatalogBinding;
import com.lavish.android.userecom.models.Cart;
import com.lavish.android.userecom.models.Inventory;
import com.lavish.android.userecom.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private ActivityCatalogBinding b;
    private Cart cart = new Cart();
    private MyApp app;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityCatalogBinding.inflate(getLayoutInflater());

        setContentView(b.getRoot());

        app = (MyApp) getApplicationContext();
        loadData();
        setupCheckout();

        FirebaseMessaging.getInstance()
                .subscribeToTopic("users");

        sendNotif();
    }

    private void sendNotif() {
        /*String response = FirebaseMessaging.getInstance().send(new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com")
                .setMessageId("abc")
                .addData("key", "value")
                .build());*/
    }

    private void loadData() {
        if(app.isOffline()){
            app.showToast(this, "Unable to save. You are offline!");
            return;
        }

        app.showLoadingDialog(this);

        app.db.collection("inventory")
                .document("products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Inventory inventory = documentSnapshot.toObject(Inventory.class);
                            products = inventory.products;
                        }
                        else
                            products = new ArrayList<>();
                        setupProductsList();
                        app.hideLoadingDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        app.hideLoadingDialog();
                        app.showToast(CatalogActivity.this, e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    private void setupCheckout() {
        b.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(CatalogActivity.this)
                        .setTitle("Cart Summary")
                        .setMessage(cart.map.toString())
                        .show();
            }
        });
    }

    private void setupProductsList() {
        //Create adapter object
        ProductsAdapter adapter = new ProductsAdapter(this, products, cart);

        //Set the adapter & LayoutManager to RV
        b.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Divider
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        b.recyclerView.addItemDecoration(itemDecor);

        b.recyclerView.setAdapter(adapter);
    }

    public void updateCartSummary(){
        if(cart.noOfItems == 0){
            b.checkout.setVisibility(View.GONE);
        } else {
            b.checkout.setVisibility(View.VISIBLE);

            b.cartSummary.setText("Total : Rs. " + cart.subTotal + "\n" + cart.noOfItems + " items");
        }
    }

}