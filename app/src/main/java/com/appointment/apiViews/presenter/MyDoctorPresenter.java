package com.appointment.apiViews.presenter;

import android.app.Activity;

import com.appointment.R;
import com.appointment.RootApp;
import com.appointment.apiViews.model.DoctorListModel;
import com.appointment.apiViews.view.IMyDoctorView;
import com.appointment.preferences.AppPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDoctorPresenter extends BasePresenter<IMyDoctorView> {

    public void getDoctorList(final Activity context) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getMyDoctorList(String.valueOf(AppPreferenceManager.getUserId(context)))
                .enqueue(new Callback<DoctorListModel>() {
                    @Override
                    public void onResponse(Call<DoctorListModel> call, Response<DoctorListModel> response) {
                        getView().enableLoadingBar(context, false, "");
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSuccess(response.body());

                                }
                            } else {
                                getView().onError(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<DoctorListModel> call, Throwable t) {
                        getView().enableLoadingBar(context, false, "");
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }

}