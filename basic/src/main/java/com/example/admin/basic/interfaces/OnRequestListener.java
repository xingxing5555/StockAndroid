package com.example.admin.basic.interfaces;

/**
 * @author Xinxin Shi
 */

public interface OnRequestListener {
    void onResponse(String tag, String json);

    void onRequestFailure(String errorMsg);
}
