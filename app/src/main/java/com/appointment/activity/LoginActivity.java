package com.appointment.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.appointment.R;
import com.appointment.apiViews.model.LoginModel;
import com.appointment.apiViews.presenter.LoginPresenter;
import com.appointment.apiViews.view.ILoginView;
import com.appointment.databinding.ActivityLoginBinding;
import com.appointment.extra.SystemUtility;
import com.appointment.extra.Utils;
import com.appointment.preferences.AppPreferenceManager;

public class LoginActivity extends BaseActivity implements ILoginView {

    ActivityLoginBinding binding;
    boolean isShowPassword= false;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        binding.toolbar.tvName.setText(getResources().getString(R.string.log_in));

        presenter = new LoginPresenter();
        presenter.setView(this);

        binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_password_gone, 0);
        binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.etPassword.setTextIsSelectable(false);

        binding.etPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (binding.etPassword.getRight() - binding.etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(isShowPassword) {
                            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.ic_password_gone, 0);
                            binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            isShowPassword= false;
                        }
                        else {
                            binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.ic_password_show, 0);
                            binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            isShowPassword = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void onClick(View v){
        Intent i;
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_second:
                i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
                break;

            case R.id.tv_sign_up:
                i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

                break;

            case R.id.cv_login:

                SystemUtility.hideVirtualKeyboard(LoginActivity.this);
                if(!Utils.isValidPhoneNumber(binding.etEmail.getText().toString().trim())){
                    toast("Please enter Mobile number”.");
                }else if(!Utils.validateString(binding.etPassword.getText().toString().trim())){
                    toast("Please enter Password");
                }else {
                    presenter.LoginCall(this,binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
                }
                break;

            case R.id.tv_forgot_password:
                i = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onLoginSucess(LoginModel item) {

        if(item.getStatus()){

            snackBar(binding.outer , "You have successfully Login.");

            AppPreferenceManager.setUserId(this,item.getData().getId());
            AppPreferenceManager.setUserEmail(this,item.getData().getEmail());
            AppPreferenceManager.setUserPhone(this,item.getData().getMobile());
            AppPreferenceManager.setUserName(this,item.getData().getName());
            AppPreferenceManager.setReporter(this,item.getData().getReporter());
            AppPreferenceManager.setPrimeExpiredDate(this,item.getData().getMembershipDate());
            //AppPreferenceManager.setPrimeExpiredDate(this,item.getExpiresAt());

            if (!TextUtils.isEmpty(item.getData().getMembershipDate())) {
                AppPreferenceManager.setPrimeActive(getApplicationContext() , "1");
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            } , 1000);

        }else {
            snackBar(binding.outer , item.getMessage());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}