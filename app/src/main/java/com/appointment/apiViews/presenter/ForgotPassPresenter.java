package com.appointment.apiViews.presenter;

import android.app.Activity;

import com.appointment.R;
import com.appointment.RootApp;
import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.view.IForgetPassView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassPresenter extends BasePresenter<IForgetPassView> {

    public void forgotPassCall(final Activity context,String mobile){
        getView().enableLoadingBar(context ,true , context.getString(R.string.loading) );

        RootApp.getInstance().getApiService().forgotPass(mobile)
                .enqueue(new Callback<GeneralModel>() {
                    @Override
                    public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
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
                    public void onFailure(Call<GeneralModel> call, Throwable t) {
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