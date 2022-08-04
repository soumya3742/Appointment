package com.appointment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.appointment.R;
import com.appointment.adapters.DoctorListAdapter;
import com.appointment.apiViews.model.DoctorDetails;
import com.appointment.apiViews.model.DoctorModel;
import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.presenter.DoctorPresenter;
import com.appointment.apiViews.view.IDoctorView;
import com.appointment.databinding.ActivityDoctorListBinding;
import com.appointment.extra.SystemUtility;

import java.util.ArrayList;

public class DoctorListActivity extends BaseActivity implements IDoctorView, DoctorListAdapter.OnItemClickListener {

    ActivityDoctorListBinding binding;
    DoctorPresenter presenter;
    ArrayList<DoctorModel.Datum> list;
    ArrayList<DoctorModel.Datum> Restorelist;
    ArrayList<DoctorModel.Datum> search_List;
    DoctorListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_list);

        presenter = new DoctorPresenter();
        presenter.setView(this);


        list = new ArrayList<>();
        Restorelist = new ArrayList<>();
        search_List = new ArrayList<>();

        adapter = new DoctorListAdapter(DoctorListActivity.this, this::onItemClick, list);
        binding.recyclerPdf.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerPdf.setAdapter(adapter);

        //presenter.getDoctorList(DoctorListActivity.this , model.getId() );

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SystemUtility.hideVirtualKeyboard(DoctorListActivity.this);
                    //presenter.getDoctorSearchList(DoctorListActivity.this  ,  binding.etSearch.getText().toString() , model.getId());
                    return true;
                }
                return false;
            }
        });


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backarraow:
                finish();
                break;
            case R.id.search_arrow:
                //presenter.getDoctorSearchList(DoctorListActivity.this , binding.etSearch.getText().toString() , model.getId());
                break;

        }
    }

    @Override
    public void onSuccess(DoctorModel item) {
        if (item.getSuccess()) {
            Restorelist.addAll(item.getData());
            list.addAll(item.getData());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccessSearch(DoctorModel item) {

        if (item.getSuccess()) {

            list.clear();
            adapter.notifyDataSetChanged();

            search_List.addAll(item.getData());
            adapter = new DoctorListAdapter(DoctorListActivity.this, this::onItemClick, search_List);
            binding.recyclerPdf.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerPdf.setAdapter(adapter);

            binding.searchLayout.setVisibility(View.GONE);
            binding.categoryLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public void onBackPressed() {


        if (binding.searchLayout.getVisibility() == View.GONE) {

            binding.searchLayout.setVisibility(View.VISIBLE);
            binding.categoryLayout.setVisibility(View.VISIBLE);

            if (search_List != null ) {
                search_List.clear();
            }

            adapter.notifyDataSetChanged();

            if (list != null ) {
                list.clear();
            }

            list.addAll(Restorelist);
            adapter = new DoctorListAdapter(DoctorListActivity.this, this::onItemClick, list);
            binding.recyclerPdf.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerPdf.setAdapter(adapter);

        }else {
            super.onBackPressed();
        }
    }



    @Override
    public void onSuccessDetails(DoctorDetails item) {

    }

    @Override
    public void onSuccessAppointmnet(GeneralModel item) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onItemClick(int index, DoctorModel.Datum model) {
        Intent intent = new Intent(this, DoctorDetailsActivity.class);
        intent.putExtra("model", model);
        startActivity(intent);
    }

}