package com.lavish.android.userecom.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {

    public static final byte WEIGHT_BASED = 0, VARIANTS_BASED = 1;

    //Compulsory
    public String name;
    public int type;

    //WeightBased
    public int pricePerKg;
    public float minQty;

    //OR

    //VariantsBased
    public List<Variant> variants;

    public Product() {

    }

    //WeightBased
    public Product(String name, int pricePerKg, float minQty) {
        type = WEIGHT_BASED;
        this.name = name;
        this.pricePerKg = pricePerKg;
        this.minQty = minQty;
    }

    //VariantsBased
    public Product(String name) {
        type = VARIANTS_BASED;
        this.name = name;
    }

    public Product(String name, List<Variant> variants) {
        type = VARIANTS_BASED;
        this.name = name;
        this.variants = variants;
    }

    public void initWeightBasedProduct(String name, int pricePerKg, float minQty) {
        type = WEIGHT_BASED;
        this.name = name;
        this.pricePerKg = pricePerKg;
        this.minQty = minQty;
    }

    public void initVariantsBasedProduct(String name) {
        type = VARIANTS_BASED;
        this.name = name;
    }

    public String minQtyToString(){
        //float (2.0) -> String (2kg)
        //float (0.050) -> String (50g)

        if(minQty < 1){
            int g = (int) (minQty * 1000);
            return g + "g";
        }

        return ((int) minQty) + "kg";
    }

    //Extracts & sets variants from String[]
    public void fromVariantStrings(String[] vs) {
        variants = new ArrayList<>();
        for(String s : vs){
            //["VarinatName", "30"]
            String[] v = s.split(",");
            variants.add(new Variant(v[0], Integer.parseInt(v[1].trim())));
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", pricePerKg=" + pricePerKg +
                ", minQty=" + minQty +
                ", variants=" + variants +
                '}';
    }

    public String variantsString(){
        String variantsString = variants.toString();
        return variantsString
                .replaceFirst("\\[", "")
                .replaceFirst("]", "")
                .replaceAll(",", ", ");
    }

    public String priceString(){
        if(type == Product.WEIGHT_BASED)
            return "Rs. " + pricePerKg + "/kg";

        return variantsString();
    }

}
