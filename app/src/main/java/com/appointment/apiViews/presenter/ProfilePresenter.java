package com.appointment.apiViews.presenter;

import android.app.Activity;
import android.content.Context;


import com.appointment.R;
import com.appointment.RootApp;
import com.appointment.apiViews.model.CityModel;
import com.appointment.apiViews.model.StateModel;
import com.appointment.apiViews.view.IProfileView;
import com.appointment.apiViews.model.ProfileModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter extends BasePresenter<IProfileView> {

    public void getProfile(Context context,int id) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getProfile(id).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                getView().enableLoadingBar(context, false, "");


                if (!handleError(response, context)) {
                    if (response.isSuccessful() && response.code() == 200) {
                        assert response.body() != null;
                        if (response.body().getSuccess()) {
                            getView().onSuccess(response.body());
                        } else {
                            assert response.body() != null;
                            getView().onError(response.body().getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

                getView().enableLoadingBar(context, false, "");
                t.printStackTrace();
                getView().onError(null);


            }
        });

    }

    public void getUpdate(Context context,int id,String name, String email, String mobile,String businessname , String state , String city , String pincode) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getUpdate(id,name,email,mobile,businessname , state , city , pincode).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                getView().enableLoadingBar(context, false, "");


                if (!handleError(response, context)) {
                    if (response.isSuccessful() && response.code() == 200) {
                        assert response.body() != null;
                        if (response.body().getSuccess()) {
                            getView().onSuccess(response.body());
                        } else {
                            assert response.body() != null;
                            getView().onError(response.body().getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

                getView().enableLoadingBar(context, false, "");
                t.printStackTrace();
                getView().onError(null);
            }
        });

    }

    public void getState(final Activity context) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getState()
                .enqueue(new Callback<StateModel>() {
                    @Override
                    public void onResponse(Call<StateModel> call, Response<StateModel> response) {
                        getView().enableLoadingBar(context, false, "");
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onStateSuccess(response.body());

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
                    public void onFailure(Call<StateModel> call, Throwable t) {
                        getView().enableLoadingBar(context, false, "");
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }

    public void getCity(final Activity context , String stateID) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getCity(stateID)
                .enqueue(new Callback<CityModel>() {
                    @Override
                    public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                        getView().enableLoadingBar(context, false, "");
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onCitySuccess(response.body());

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
                    public void onFailure(Call<CityModel> call, Throwable t) {
                        getView().enableLoadingBar(context, false, "");
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }
}
