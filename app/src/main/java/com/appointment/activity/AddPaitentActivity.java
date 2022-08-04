package com.appointment.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.appointment.R;
import com.appointment.apiViews.model.DoctorDetails;
import com.appointment.apiViews.model.DoctorModel;
import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.presenter.DoctorPresenter;
import com.appointment.apiViews.view.IDoctorView;
import com.appointment.databinding.ActivityAddPaitentBinding;
import com.appointment.preferences.AppPreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;

public class AddPaitentActivity extends BaseActivity implements IDoctorView{

    ActivityAddPaitentBinding binding;
    private int mYear, mMonth, mDay;
    DoctorDetails item;
    String arrayName[];
    String arrayId[];
    String arrayFees[];
    String typeId;
    DoctorPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_paitent);
        binding.toolbar.tvName.setText("   Add Patient Detail");

        if (getIntent().hasExtra("model")) {
            item = (DoctorDetails) getIntent().getSerializableExtra("model");
        }

        presenter = new DoctorPresenter();
        presenter.setView(this);

        ArrayList<String> arrlistName = new ArrayList<String>();
        ArrayList<String> arrlistId = new ArrayList<String>();
        ArrayList<String> arrlistType = new ArrayList<String>();


        for (int i = 0; i < item.getData().getTimings().size(); i++) {
            arrlistName.add(item.getData().getTimings().get(i).getHospitalName());
            arrlistId.add(item.getData().getTimings().get(i).getId());
            arrlistType.add(item.getData().getTimings().get(i).getFees());
        }

        /*ArrayList to Array Conversion */
        arrayName = new String[arrlistName.size()];
        for (int j = 0; j < arrlistName.size(); j++) {
            arrayName[j] = arrlistName.get(j);
        }

        /*ArrayList to Array Conversion */
        arrayId = new String[arrlistId.size()];
        for (int j = 0; j < arrlistId.size(); j++) {
            arrayId[j] = arrlistId.get(j);
        }

        /*ArrayList to Array Conversion */
        arrayFees = new String[arrlistType.size()];
        for (int j = 0; j < arrlistType.size(); j++) {
            arrayFees[j] = arrlistType.get(j);
        }


    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;

            case R.id.location:

                final CharSequence[] name = arrayName;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddPaitentActivity.this);
                alertDialog.setTitle("Select Type");
                alertDialog.setSingleChoiceItems(name, -1, (dialog, which) -> {

                    binding.location.setText(arrayName[which]);
                    typeId = arrayId[which];
                    binding.fees.setText(arrayFees[which]);
                    dialog.dismiss();


                });
                alertDialog.show();


                break;

            case R.id.gender:
                final CharSequence[] gender = {"Male", "Female"};
                AlertDialog.Builder alert = new AlertDialog.Builder(AddPaitentActivity.this);
                alert.setTitle("Select Gender");
                alert.setSingleChoiceItems(gender, -1, (dialog, which) -> {
                    if (gender[which] == "Male") {
                        binding.gender.setText(gender[0]);
                        dialog.dismiss();
                    } else if (gender[which] == "Female") {
                        binding.gender.setText(gender[1]);
                        dialog.dismiss();
                    }
                });
                alert.show();

                break;

            case R.id.dob:
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                binding.dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

                break;

                case R.id.currentDate:
                // Get Current Date
                final Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                binding.currentDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;



            case R.id.footer:

                if (!TextUtils.isEmpty(typeId)) {

                    if (!TextUtils.isEmpty(binding.name.getText().toString())) {

                        if (!TextUtils.isEmpty(binding.mobile.getText().toString())) {

                            if (!TextUtils.isEmpty(binding.gender.getText().toString())) {

                                if (!TextUtils.isEmpty(binding.dob.getText().toString())) {

                                    AddPatient();

                                }else {
                                    snackBar(binding.coordinator, "Please select Date of Birth");
                                }

                            }else {
                                snackBar(binding.coordinator, "Please select Gener");
                            }

                        }else {
                            snackBar(binding.coordinator, "Please enter mobile number");
                        }
                    }else {
                        snackBar(binding.coordinator, "Please enter name");
                    }
                }else {
                    snackBar(binding.coordinator, "Please select type");
                }


                break;


        }


    }

    @Override
    public void onSuccess(DoctorModel item) {

    }

    @Override
    public void onSuccessSearch(DoctorModel item) {

    }

    @Override
    public void onSuccessDetails(DoctorDetails item) {

    }

    @Override
    public void onSuccessAppointmnet(GeneralModel item) {
        if (item  != null) {
            snackBar(binding.outer , item.getMessage());
            toast(item.getMessage());
            finish();
        }else {
            snackBar(binding.outer , item.getMessage());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }


    private void AddPatient() {

        presenter.drAppointment(AddPaitentActivity.this, item.getData().getId(),
                AppPreferenceManager.getUserId(getApplicationContext()),
                item.getData().getTimings().get(0).getTiming(),
                typeId ,
                binding.name.getText().toString(),
                binding.mobile.getText().toString(),
                binding.emailid.getText().toString(),
                binding.gender.getText().toString(),
                binding.dob.getText().toString(),
                binding.currentDate.getText().toString(),
                binding.fees.getText().toString());
    }
//item.getData().getTimings().get(0).getTiming() + " - " + item.getData().getTimings().get(1).getTiming(),
}