package com.appointment.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.appointment.R;
import com.appointment.RootApp;
import com.appointment.activity.LoginActivity;
import com.appointment.preferences.AppPreferenceManager;

import dmax.dialog.SpotsDialog;

public class BaseFragment extends Fragment {

    SpotsDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public BaseFragment() {}

    public String tag() {
        return getClass().getSimpleName();
    }

    public void log(String message) {
        Log.d(tag(), message);
    }

    public void toast(final String message) {
        try {
            if (isAdded() && getActivity() != null) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadProgressBar(Context context, String message, boolean cancellable) {

        try {
            if (context != null && isAdded()) {
                if (mProgressDialog == null)
                    mProgressDialog = new SpotsDialog(getActivity(), message, R.style.SpotCustomDialog);
                if (!mProgressDialog.isShowing())
                    mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressBar(Context context) {
        try {
            if (context != null && mProgressDialog != null) {
                if (isAdded() && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AlertDialog.Builder getAlertDialogBuilder(String title, String message, boolean cancellable) {
        return new AlertDialog.Builder(getActivity(), R.style.AppTheme_AlertDialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancellable);

    }

    public void enableLoadingBar(Context context, boolean enable, String s) {
        if (isAdded()) {
            if (enable) {
                loadProgressBar(context, s, false);
            } else {
                dismissProgressBar(context);
            }
        }
    }

    public void onLogout() {
        AppPreferenceManager.clearAll(RootApp.getInstance());
        Intent intent = new Intent(RootApp.getInstance(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityCompat.finishAffinity(getActivity());
        startActivity(intent);
        getActivity().finish();
    }

    public void SnackBar(View coordinatorLayout, String txt) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, txt, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
