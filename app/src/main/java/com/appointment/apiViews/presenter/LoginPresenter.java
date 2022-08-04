package com.appointment.apiViews.presenter;

import android.app.Activity;

import com.appointment.R;
import com.appointment.RootApp;
import com.appointment.apiViews.model.LoginModel;
import com.appointment.apiViews.view.ILoginView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<ILoginView> {

    public void LoginCall(final Activity context,String mobile , String password){
        getView().enableLoadingBar(context ,true , context.getString(R.string.loading) );

        RootApp.getInstance().getApiService().login(mobile,password)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        getView().enableLoadingBar(context, false, "");
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onLoginSucess(response.body());

                                }
                            }
                            else {
                                getView().onError(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        try {
                            getView().enableLoadingBar(context, false, "");
                            t.printStackTrace();
                            getView().onError(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}