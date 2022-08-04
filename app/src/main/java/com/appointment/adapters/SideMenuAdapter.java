package com.appointment.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.appointment.BR;
import com.appointment.R;
import com.appointment.databinding.SideMenuItemBinding;
import com.appointment.preferences.AppPreferenceManager;

import java.util.Arrays;
import java.util.List;

public class SideMenuAdapter extends RecyclerView.Adapter<SideMenuAdapter.MyViewHolder> {

    private List<String> CatName;
    private TypedArray navMenuIcons;
    private OnItemClickListener OnItemClickListener;
    private Activity activity;
    private boolean isReporter;

    public SideMenuAdapter(Activity activity, boolean isReporter, OnItemClickListener OnItemClickListener) {

        this.OnItemClickListener = OnItemClickListener;
        this.activity = activity;
        this.isReporter = isReporter;

        CatName = Arrays.asList(activity.getResources().getStringArray(R.array.menu_list_reporter));
        navMenuIcons = activity.getResources().obtainTypedArray(R.array.nav_icons_reporter);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.side_menu_item, parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.getBinding().setVariable(BR.index,position);
        holder.getBinding().setVariable(BR.OnItemClickListener,OnItemClickListener);

        SideMenuItemBinding binding = (SideMenuItemBinding) holder.getBinding();
        binding.tvText.setText(CatName.get(position));

        binding.image.setImageResource(navMenuIcons.getResourceId(position,0));


        if (CatName.get(position).equals("Logout")) {
            if (AppPreferenceManager.getUserId(this.activity) == 0) {
                binding.tvText.setText("Login");
            }
        }
    }

    @Override
    public int getItemCount() {
        return CatName.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int index);
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