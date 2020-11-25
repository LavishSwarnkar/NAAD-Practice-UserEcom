package com.lavish.android.userecom.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.lavish.android.userecom.databinding.DialogVariantPickerBinding;
import com.lavish.android.userecom.databinding.VariantItemBinding;
import com.lavish.android.userecom.models.Cart;
import com.lavish.android.userecom.models.Product;
import com.lavish.android.userecom.models.Variant;

public class VariantPickerDialog {

    private Context context;
    private Cart cart;
    private Product product;
    private DialogVariantPickerBinding b;

    public void show(Context context, final Cart cart, final Product product, final OnVariantPickedListener listener){
        this.context = context;
        this.cart = cart;
        this.product = product;

        b = DialogVariantPickerBinding.inflate(
                LayoutInflater.from(context)
        );

        new AlertDialog.Builder(context)
                .setTitle(product.name)
                .setCancelable(false)
                .setView(b.getRoot())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int qty = cart.totalVariantsQtyMap.get(product.name);
                        if(qty > 0)
                            listener.onQtyUpdated(qty);
                        else
                            listener.onRemoved();
                    }
                })
                .setNegativeButton("REMOVE ALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cart.removeAllVariantsFromCart(product);
                        listener.onRemoved();
                    }
                })
                .show();

        showVariants();
    }

    private void showVariants() {
        for(Variant variant : product.variants){
            //Inflate
            VariantItemBinding ib = VariantItemBinding.inflate(
                     /* Inflater */ LayoutInflater.from(context)
                    ,/*  Parent  */ b.getRoot()
                    , true
            );

            //Bind data
            ib.variantName.setText(variant.nameAndPriceString());

            showPreviousQty(variant, ib);
            setupButtons(variant, ib);
        }
    }

    private void showPreviousQty(Variant variant, VariantItemBinding ib) {
        int qty = cart.getVariantQty(product, variant);
        if(qty > 0){
            ib.remove.setVisibility(View.VISIBLE);
            ib.qty.setVisibility(View.VISIBLE);

            ib.qty.setText(qty + "");
        }
    }

    private void setupButtons(final Variant variant, final VariantItemBinding ib) {
        ib.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Update qtyView
                int qty = cart.addToCart(product, variant);
                ib.qty.setText(qty + "");

                //Update buttons visibility
                if(qty == 1){
                    ib.remove.setVisibility(View.VISIBLE);
                    ib.qty.setVisibility(View.VISIBLE);
                }
            }
        });

        ib.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Update qtyView
                int qty = cart.removeFromCart(product, variant);
                ib.qty.setText(qty + "");

                //Update buttons visibility
                if(qty == 0){
                    ib.remove.setVisibility(View.GONE);
                    ib.qty.setVisibility(View.GONE);
                }
            }
        });
    }

    public interface OnVariantPickedListener {
        void onQtyUpdated(int qty);
        void onRemoved();
    }

}
