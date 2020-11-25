package com.lavish.android.userecom.models;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    //Map to save all cartItems
    public Map<String, CartItem> map = new HashMap<>();

    //Map to save VB Product's total Qty
    public Map<String, Integer> totalVariantsQtyMap = new HashMap<>();

    public int noOfItems, subTotal;



    //Variant based

    /** Method to add a VB Product to Cart
     *
     * @param product Product to be added
     * @param variant Product's Variant to be added
     * @return Updated Qty of Variant
     */
    public int addToCart(Product product, Variant variant){
        //Unique key
        String key = product.name + " " + variant.name;

        //Update map
        if(map.containsKey(key)){
            //Update qty & price in map
            CartItem existingCartItem = map.get(key);
            existingCartItem.qty++;
            existingCartItem.price += variant.price;
        } else {
            //Put in map
            map.put(key, new CartItem(key, variant.price));
        }

        //Update summary
        noOfItems++;
        subTotal += variant.price;

        //Update total VBP Qty
        if(totalVariantsQtyMap.containsKey(product.name)){
            int qty = totalVariantsQtyMap.get(product.name) + 1;
            totalVariantsQtyMap.put(product.name, qty);
        } else {
            totalVariantsQtyMap.put(product.name, 1);
        }

        return (int) map.get(key).qty;
    }

    /** Method to remove a VB Product from Cart
     *
     * @param product Product to be removed
     * @param variant Product's Variant to be removed
     * @return Updated Qty of Variant
     */
    public int removeFromCart(Product product, Variant variant){
        //Unique key
        String key = product.name + " " + variant.name;

        //Update qty & price in map
        CartItem existingCartItem = map.get(key);
        existingCartItem.qty--;
        existingCartItem.price -= variant.price;


        //Check for complete removal
        if(map.get(key).qty == 0)
            map.remove(key);

        //Update summary
        noOfItems--;
        subTotal -= variant.price;

        //Update totalQty map
        int qty = totalVariantsQtyMap.get(product.name) - 1;
        totalVariantsQtyMap.put(product.name, qty);

        //Check for complete removal
        if(totalVariantsQtyMap.get(product.name) == 0)
            totalVariantsQtyMap.remove(key);

        return map.containsKey(key) ? (int) map.get(key).qty : 0;
    }

    public void removeAllVariantsFromCart(Product product){
        for(Variant variant : product.variants){
            String key = product.name + " " + variant.name;

            if(map.containsKey(key)){
                subTotal -= map.get(key).price;
                noOfItems -= map.get(key).qty;

                map.remove(key);
            }
        }

        if(totalVariantsQtyMap.containsKey(product.name))
            totalVariantsQtyMap.remove(product.name);
    }

    public int getVariantQty(Product product, Variant variant){
        String key = product.name + " " + variant.name;

        if(map.containsKey(key))
            return (int) map.get(key).qty;

        return 0;
    }

    //Weight based

    /** Method to update a WB Product to Cart
     *
     * @param product Product to be updated
     * @param qty Quantity of product
     */
    public void updateWBPQuantity(Product product, float qty) {
        //Calculate newPrice
        int newPrice = (int) (product.pricePerKg * qty);

        //Decrement previous price
        if(map.containsKey(product.name))
            subTotal -= map.get(product.name).price;
        //Added for the first time, so increment noOfItems
        else
            noOfItems++;

        //Overwrite previous info OR put new info
        map.put(product.name, new CartItem(product.name, newPrice, qty));
        subTotal += newPrice;
    }

    public void removeWBPFromCart(Product product) {
        if(map.containsKey(product.name)){
            subTotal -= map.get(product.name).price;
            noOfItems--;

            map.remove(product.name);
        }
    }
}
