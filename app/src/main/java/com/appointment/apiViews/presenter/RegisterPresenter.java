package com.appointment.apiViews.presenter;

import android.app.Activity;
import android.content.Context;

import com.appointment.R;
import com.appointment.RootApp;
import com.appointment.apiViews.model.CityModel;
import com.appointment.apiViews.model.SignUpModel;
import com.appointment.apiViews.model.StateModel;
import com.appointment.apiViews.view.IRegisterView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter extends BasePresenter<IRegisterView> {


    public void getSignup(Context context, String name, String email, String password, String mobile, String referralCode, String stateslectedID, String citySelectedID) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getSignUp(name, email, mobile, password , referralCode  , stateslectedID , citySelectedID)
                .enqueue(new Callback<SignUpModel>() {
                    @Override
                    public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                        getView().enableLoadingBar(context, false, "");


                        if (!handleError(response, context)) {
                            if (response.isSuccessful() && response.code() == 200) {
                                assert response.body() != null;
                                getView().onSuccess(response.body());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpModel> call, Throwable t) {

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