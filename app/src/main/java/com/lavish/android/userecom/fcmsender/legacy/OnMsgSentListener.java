package com.lavish.android.userecom.fcmsender.legacy;

public interface OnMsgSentListener {
    void onSuccess(String response);
    void onFailure(String error);
}
