package com.appointment.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.appointment.BR;
import com.appointment.R;
import com.appointment.apiViews.model.DoctorModel;
import com.appointment.databinding.DoctorItemBinding;
import com.appointment.extra.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> {

    OnItemClickListener OnItemClickListener;
    ArrayList<DoctorModel.Datum> list ;
    Activity activity;

    public DoctorListAdapter(Activity activity, OnItemClickListener OnItemClickListener, ArrayList<DoctorModel.Datum> list) {
        this.list = list;
        this.OnItemClickListener = OnItemClickListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.doctor_item, parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DoctorItemBinding binding = (DoctorItemBinding) holder.getBinding();

        binding.txtDocotorName.setText(list.get(position).getDoctorName());
        binding.txtSpecial.setText("Specializes in "+list.get(position).getDoctortype());
        binding.txtEducation.setText(list.get(position).getEducation());
        Glide.with(activity).load(Constants.BASE_URL_IMAGE+list.get(position).getImage()).into(binding.image);

        binding.cardRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnItemClickListener.onItemClick(position, list.get(position));
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int index, DoctorModel.Datum model);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}