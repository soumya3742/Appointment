package com.appointment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appointment.R;
import com.appointment.apiViews.presenter.SplashPresenter;
import com.appointment.apiViews.view.ISplashView;
import com.appointment.preferences.AppPreferenceManager;

public class SplashActivity extends BaseActivity implements ISplashView {

    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenter();
        presenter.setView(this);

        // move user to next screen
        presenter.moveToNextScreen();
    }

    @Override
    public void onSplashTimeOut() {
        Intent i;
        if(AppPreferenceManager.getUserId(this) == 0){
            i = new Intent(SplashActivity.this, RegisterActivity.class);
        }else {
            i = new Intent(SplashActivity.this, HomeActivity.class);
        }
        startActivity(i);
        SplashActivity.this.finish();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
