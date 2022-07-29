package com.rikkei.awesome.utils;


import com.rikkei.awesome.model.User;

public interface OnUserClickedListener {
    void onUserLongClicked(User user, int position);
    void onMessageClicked(User user);
}
