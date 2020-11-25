package com.lavish.android.userecom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lavish.android.userecom.databinding.ActivityLinearLayoutDemoBinding;
import com.lavish.android.userecom.databinding.VariantItemBinding;
import com.lavish.android.userecom.models.Variant;

import java.util.Arrays;
import java.util.List;

public class LinearLayoutDemoActivity extends AppCompatActivity {

    private ActivityLinearLayoutDemoBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLinearLayoutDemoBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        showVariants();
    }

    private void showVariants() {
        //Data
        List<Variant> variants = Arrays.asList(
                new Variant("ABC", 1)
                , new Variant("DEF", 2)
                , new Variant("GHI", 3)
                , new Variant("JKL", 4)
        );

        for(Variant variant : variants){
            //Inflate layout for each variant
            VariantItemBinding ib = VariantItemBinding.inflate(getLayoutInflater());

            //Bind data to view
            ib.variantName.setText(variant.nameAndPriceString());

            //Add view to LinearLayout
            b.list.addView(ib.getRoot());

            //OR : VariantItemBinding ib = VariantItemBinding.inflate(getLayoutInflater(), b.list, true);
        }
    }
}