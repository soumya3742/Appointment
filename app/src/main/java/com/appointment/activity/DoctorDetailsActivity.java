package com.appointment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.appointment.R;
import com.appointment.apiViews.model.DoctorDetails;
import com.appointment.apiViews.model.DoctorModel;
import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.presenter.DoctorPresenter;
import com.appointment.apiViews.view.IDoctorView;
import com.appointment.databinding.ActivityDoctorDetailsBinding;
import com.appointment.databinding.HospitalItemBinding;
import com.appointment.extra.Constants;
import com.bumptech.glide.Glide;

public class DoctorDetailsActivity extends BaseActivity implements IDoctorView {

    ActivityDoctorDetailsBinding binding;
    DoctorModel.Datum model;
    DoctorPresenter presenter;
    DoctorDetails item;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_details);

        presenter = new DoctorPresenter();
        presenter.setView(this);

        if (getIntent().hasExtra("model")) {
            model = (DoctorModel.Datum) getIntent().getSerializableExtra("model");
            id = model.getId();
        }if (getIntent().hasExtra("fromScreen")) {
            binding.footer.setVisibility(View.GONE);
        }

        binding.toolbar.tvName.setText("   Doctor Detail");

        presenter.getDoctorDetails(DoctorDetailsActivity.this, id);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.footer:
                Intent intent = new Intent(this, AddPaitentActivity.class);
                intent.putExtra("model", this.item);
                startActivity(intent);
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
        this.item = item;
        Glide.with(DoctorDetailsActivity.this).load(Constants.BASE_URL_IMAGE+item.getData().getImage()).into(binding.image);
        binding.txtName.setText(item.getData().getDoctorName());
        binding.txtSpecial.setText("Specializes in "+item.getData().getCategoryName());
        binding.txtEducation.setText(item.getData().getEducation());
        binding.txtExperience.setText(item.getData().getExperience()+" yrs");
        binding.txtSystemMedi.setText(item.getData().getSystemMedicine());
        binding.txtEmail.setText(item.getData().getEmail());

        for (int i = 0; i < item.getData().getTimings().size(); i++) {

            binding.llLink.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            binding.llLink.setLayoutParams(params1);
            LayoutInflater layoutInflater = getLayoutInflater();

            HospitalItemBinding studyItem = DataBindingUtil.inflate(layoutInflater, R.layout.hospital_item, null, false);


            if (item.getData().getTimings().get(i).getType().equalsIgnoreCase("Clinic")) {
                studyItem.nameType.setText("Clinic Name :");
                studyItem.tvAddressType.setText("Clinic Address :");
            } else if (item.getData().getTimings().get(i).getType().equalsIgnoreCase("Hospital")) {
                studyItem.nameType.setText("Hospital Name :");
                studyItem.tvAddressType.setText("Hospital Address :");
            } else {
                studyItem.nameType.setText("Diagnostic Name :");
                studyItem.tvAddressType.setText("Diagnostic Address :");
            }

            studyItem.name.setText(item.getData().getTimings().get(i).getHospitalName());
            studyItem.timing.setText(item.getData().getTimings().get(i).getTiming());
            studyItem.tvAddress.setText(item.getData().getTimings().get(i).getAddress());
            //studyItem.fees.setText("₹" + item.getData().getTimings().get(i).getFees());

            binding.llLink.addView(studyItem.getRoot());

        }

    }

    @Override
    public void onSuccessAppointmnet(GeneralModel item) {

        if (item != null) {
            snackBar(binding.outer, item.getMessage());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}