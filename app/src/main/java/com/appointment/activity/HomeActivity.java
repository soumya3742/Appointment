package com.appointment.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appointment.adapters.DoctorListAdapter;
import com.appointment.apiViews.model.DoctorDetails;
import com.appointment.apiViews.model.DoctorModel;
import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.presenter.DoctorPresenter;
import com.appointment.apiViews.view.IDoctorView;
import com.appointment.extra.SystemUtility;
import com.github.javiersantos.appupdater.AppUpdater;
import com.appointment.R;
import com.appointment.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.Timer;

public class HomeActivity extends BaseActivity implements IDoctorView, DoctorListAdapter.OnItemClickListener {

    ActivityHomeBinding binding;
    int currentPage = -1;
    Timer timer;
    boolean doubleBackToExitPressedOnce = false;
    DoctorPresenter presenter;
    ArrayList<DoctorModel.Datum> list;
    ArrayList<DoctorModel.Datum> Restorelist;
    ArrayList<DoctorModel.Datum> search_List;
    DoctorListAdapter doctorListAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);


        presenter = new DoctorPresenter();
        presenter.setView(this);
        binding.searchLayout.setVisibility(View.GONE);

        list = new ArrayList<>();
        Restorelist = new ArrayList<>();
        search_List = new ArrayList<>();

        doctorListAdapter = new DoctorListAdapter(HomeActivity.this, this::onItemClick, list);
        binding.recyDoctors.setLayoutManager(new LinearLayoutManager(this));
        binding.recyDoctors.setAdapter(doctorListAdapter);

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SystemUtility.hideVirtualKeyboard(HomeActivity.this);
                    presenter.getDoctorSearchList(HomeActivity.this  ,  binding.etSearch.getText().toString() , 1);
                    return true;
                }
                return false;
            }
        });

        presenter.getDoctorList(HomeActivity.this , 1);

        binding.toolbar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        binding.toolbar.tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backarraow:
                finish();
                break;
            case R.id.search_arrow:
                presenter.getDoctorSearchList(HomeActivity.this , binding.etSearch.getText().toString() , 1);
                break;

        }
    }

    public DrawerLayout getDrawer() {
        return binding.drawerLayout;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            timer.cancel();

            System.exit(0);
            System.runFinalizersOnExit(true);
            moveTaskToBack(true);
            finish();
        }
        doubleBackToExitPressedOnce = true;
        toast("Press again to close App");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onItemClick(int index, DoctorModel.Datum model) {
        Intent intent = new Intent(this, DoctorDetailsActivity.class);
        intent.putExtra("model", model);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }


    public void loginAlert() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Alert");
        adb.setMessage("Please Login  to access E Paper !!!");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.show();
        adb.create();
    }

    @Override
    public void onSuccess(DoctorModel item) {
        list.clear();
        list.addAll(item.getData());
        doctorListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessSearch(DoctorModel item) {
        list.clear();
        list.addAll(item.getData());
        doctorListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessDetails(DoctorDetails item) {

    }

    @Override
    public void onSuccessAppointmnet(GeneralModel item) {

    }
}
