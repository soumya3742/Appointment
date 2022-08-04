package com.appointment.apiViews.presenter;

import android.os.Handler;
import android.os.Looper;

import com.appointment.apiViews.view.ISplashView;
import com.appointment.extra.Constants;


public class SplashPresenter extends BasePresenter<ISplashView> {

    public void moveToNextScreen() {
        try {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    getView().onSplashTimeOut();
                }
            }, Constants.SPLASH_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
