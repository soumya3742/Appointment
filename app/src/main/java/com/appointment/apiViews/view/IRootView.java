package com.appointment.apiViews.view;

import android.content.Context;

public interface IRootView {

    Context getContext();
    void enableLoadingBar(Context context, boolean enable, String s);

    void onError(String reason);
}