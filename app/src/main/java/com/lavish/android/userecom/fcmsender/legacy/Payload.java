package com.lavish.android.userecom.fcmsender.legacy;

public class Payload{
    public OnMsgSentListener onMsgSentListener;
    public String message;

    public Payload(String message, OnMsgSentListener onMsgSentListener) {
        this.message = message;
        this.onMsgSentListener = onMsgSentListener;
    }



}
