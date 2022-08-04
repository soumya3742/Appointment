package com.appointment.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.appointment.R;
import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.presenter.ForgotPassPresenter;
import com.appointment.apiViews.view.IForgetPassView;
import com.appointment.databinding.ActivityForgotPasswordBinding;
import com.appointment.extra.SystemUtility;
import com.appointment.extra.Utils;

public class ForgotPassword extends BaseActivity implements IForgetPassView {

    ActivityForgotPasswordBinding binding;
    ForgotPassPresenter presenter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password);

        binding.toolbar.tvSecond.setVisibility(View.GONE);
        binding.toolbar.tvName.setText("Forgot Password");

        presenter = new ForgotPassPresenter();
        presenter.setView(this);
    }

    public void onClick(View v){
        Intent i;
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_sign_up:
                i = new Intent(ForgotPassword.this, RegisterActivity.class);
                startActivity(i);
                break;

            case R.id.cv_forgot:
                SystemUtility.hideVirtualKeyboard(ForgotPassword.this);
                if(!Utils.isValidPhoneNumber(binding.etPhone.getText().toString().trim())){
                    toast("Please enter Mobile number”.");
                }else {
                    presenter.forgotPassCall(this,binding.etPhone.getText().toString());
                }
                break;

        }
    }

    @Override
    public void onLoginSucess(GeneralModel item) {

        snackBar(binding.coordinator, item.getMessage());

        if (item.getSuccess()) {
            new AlertDialog.Builder(ForgotPassword.this)
                    .setTitle("Message")
                    .setMessage(item.getMessage())
                    .setCancelable(false)
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    @Override
    public Context getContext() {
        return this;
    }
}
