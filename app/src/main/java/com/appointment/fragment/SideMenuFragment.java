package com.appointment.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.appointment.R;
import com.appointment.activity.HomeActivity;
import com.appointment.activity.LoginActivity;
import com.appointment.activity.MyProfileActivity;
import com.appointment.adapters.SideMenuAdapter;
import com.appointment.databinding.FragmentSideMenuBinding;
import com.appointment.preferences.AppPreferenceManager;


public class SideMenuFragment extends BaseFragment implements SideMenuAdapter.OnItemClickListener {

    private FragmentSideMenuBinding binding;
    private SideMenuAdapter adapter;
    private boolean isReporter = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_side_menu, container, false);
        binding.setFragment(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.rvMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (AppPreferenceManager.getReporter(getContext()).equalsIgnoreCase("yes")){
            isReporter = true;
        }
        adapter = new SideMenuAdapter(getActivity(), isReporter, this);
        binding.rvMenu.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int index) {
        Intent intent;
        if (AppPreferenceManager.getReporter(getContext()).equalsIgnoreCase("No")) {
            if (index > 2) {
                index += 1;
            }
        }
        if (index == 0) {
            if (AppPreferenceManager.getUserId(getActivity()) == 0) {
                loginAlert();
            } else {
                intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
                getParentActivity().getDrawer().closeDrawers();
            }
        } else if (index == 1) {
            if (AppPreferenceManager.getUserId(getActivity()) == 0) {
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getParentActivity().getDrawer().closeDrawers();
            } else {
                onLogout();
            }
        } else {

        }
    }

    private HomeActivity getParentActivity() {
        if (getActivity() instanceof HomeActivity)
            return (HomeActivity) getActivity();
        else
            return null;
    }

    public void loginAlert() {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setTitle("Alert");
        adb.setMessage("Please Login First !!!");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
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




}
