package com.appointment.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.snackbar.Snackbar;
import com.appointment.R;
import com.appointment.apiViews.model.CityModel;
import com.appointment.apiViews.model.SignUpModel;
import com.appointment.apiViews.model.StateModel;
import com.appointment.apiViews.presenter.RegisterPresenter;
import com.appointment.apiViews.view.IRegisterView;
import com.appointment.databinding.ActivityRegisterBinding;
import com.appointment.extra.Constants;
import com.appointment.extra.NetworkAlertUtility;
import com.appointment.extra.Utils;
import com.appointment.preferences.AppPreferenceManager;
import java.util.ArrayList;
import java.util.Objects;
import static com.appointment.extra.Utils.isEmailValid;

public class RegisterActivity extends BaseActivity implements IRegisterView {

    ActivityRegisterBinding binding;
    boolean isShowPassword = false;
    RegisterPresenter presenter;

    ArrayList stateName, stateId;
    ArrayList cityName, cityId;

    String stateslectedID = "";
    String citySelectedID = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        presenter = new RegisterPresenter();
        presenter.setView(this);

        stateName = new ArrayList();
        stateId = new ArrayList();

        cityName = new ArrayList();
        cityId = new ArrayList();

        binding.toolbar.tvName.setText(getResources().getString(R.string.sign_up));

        binding.inputPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_password_gone, 0);
        binding.inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.inputPassword.setTextIsSelectable(false);

        binding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.inputPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.inputPassword.getRight() - binding.inputPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (isShowPassword) {
                            binding.inputPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_password_gone, 0);
                            binding.inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            isShowPassword = false;
                        } else {
                            binding.inputPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_password_show, 0);
                            binding.inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            isShowPassword = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });


        //presenter.getState(RegisterActivity.this);

        cityName.add(0, "Please Select District");
        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cityName);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        binding.tvcity.setAdapter(dataAdapter);
        //attach the listener to the spinner
        binding.tvcity.setOnItemSelectedListener(new MyOnItemSelectedListenerCity());


        //attach the listener to the spinner
        binding.tvstate.setOnItemSelectedListener(new MyOnItemSelectedListener());
        binding.tvcity.setOnItemSelectedListener(new MyOnItemSelectedListenerCity());

    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (pos > 0) {
                String selectedItem = parent.getItemAtPosition(pos - 1).toString();

                stateslectedID = (String) stateId.get(stateName.indexOf(selectedItem));
                presenter.getCity(RegisterActivity.this, stateslectedID);

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    public class MyOnItemSelectedListenerCity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (pos > 0) {

                String selectedItem = parent.getItemAtPosition(pos - 1).toString();
                citySelectedID = (String) cityId.get(cityName.indexOf(selectedItem));

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_second:
                i = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(i);
                break;

            case R.id.tv_sign_in:
                i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.cv_signIn:
                if (Objects.requireNonNull(binding.etName.getText()).toString().trim().length() == 0) {
                    snackBar(binding.outer, "Please enter your full name");
                } else if (!Utils.isValidPhoneNumber(binding.inputMobile.getText().toString().trim())) {
                    snackBar(binding.outer, "Please enter 10 digit mobile no.");
                } else if (Objects.requireNonNull(binding.inputEmail.getText()).toString().trim().length() == 0) {
                    snackBar(binding.outer, "Please enter email");
                } else if (!isEmailValid(binding.inputEmail.getText().toString().trim())) {
                    snackBar(binding.outer, "Please enter valid email");
                } else if (binding.inputPassword.getText().toString().trim().length() == 0) {
                    snackBar(binding.outer, "Please enter password");
                } else if (!binding.tc.isChecked()) {
                    snackBar(binding.outer, "Please accept Terms and Conditions");
                } else if (NetworkAlertUtility.isConnectingToInternet(this)) {
                    presenter.getSignup(this, binding.etName.getText().toString(), binding.inputEmail.getText().toString().trim(),
                            binding.inputPassword.getText().toString().trim(), binding.inputMobile.getText().toString().trim(), binding.referalCode.getText().toString().trim() ,"19" ,"450");
                } else {
                    snackBar(binding.outer, "Please connect internet");
                }

                break;
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccess(SignUpModel item) {

        if (item.getStatus()) {
            AppPreferenceManager.setUserId(this, item.getData().getId());
            AppPreferenceManager.setUserName(this, binding.etName.getText().toString());
            AppPreferenceManager.setUserEmail(this, binding.inputEmail.getText().toString());
            AppPreferenceManager.setUserPhone(this, binding.inputMobile.getText().toString());
            AppPreferenceManager.setReporter(this, item.getData().getReporter());

            snackBar(binding.outer, item.getMessage());


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            } , 1000);


        } else {
            Snackbar.make(binding.outer, "Error :- " + item.getMessage(), Snackbar.LENGTH_SHORT)
                    .show();

        }

    }

    @Override
    public void onStateSuccess(StateModel item) {

        if (stateName != null && stateName.size() > 0) {
            stateName.clear();
            stateId.clear();

        }

        for (int i = 0; i < item.getData().size(); i++) {
            stateName.add(item.getData().get(i).getName());
            stateId.add(item.getData().get(i).getId());
        }

        stateName.add(0, "Please Select State");
        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stateName);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        binding.tvstate.setAdapter(dataAdapter);

    }

    @Override
    public void onCitySuccess(CityModel item) {

        if (cityName != null && cityName.size() > 0) {
            cityName.clear();
            cityId.clear();

        }
        for (int i = 0; i < item.getData().size(); i++) {
            cityName.add(item.getData().get(i).getCity());
            cityId.add(item.getData().get(i).getId());
        }

        cityName.add(0, "Please Select District");

        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cityName);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        binding.tvcity.setAdapter(dataAdapter);
        //attach the listener to the spinner
        binding.tvcity.setOnItemSelectedListener(new MyOnItemSelectedListenerCity());

    }
}
