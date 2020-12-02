package com.lavish.android.userecom.models;

import com.google.firebase.Timestamp;

import java.util.List;

public class Order {

    int status;
    String orderId;
    Timestamp orderPlacedTs;

    String userName, userPhoneNo, userAddress;

    List<CartItem> cartItems;
    int subTotal;

}
