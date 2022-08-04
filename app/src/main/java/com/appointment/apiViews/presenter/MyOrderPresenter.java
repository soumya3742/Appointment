package com.appointment.apiViews.presenter;

import android.app.Activity;

import com.appointment.R;
import com.appointment.RootApp;
import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.model.OrderList;
import com.appointment.apiViews.view.IMyOrderView;
import com.appointment.preferences.AppPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderPresenter extends BasePresenter<IMyOrderView> {

    public void getOrderList(final Activity context){
        getView().enableLoadingBar(context ,true , context.getString(R.string.loading) );

        RootApp.getInstance().getApiService().getMyorder(AppPreferenceManager.getUserId(context))
                .enqueue(new Callback<OrderList>() {
                    @Override
                    public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                        getView().enableLoadingBar(context, false, "");
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSuccess(response.body());

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
                    public void onFailure(Call<OrderList> call, Throwable t) {
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

    public void orderCancel(final Activity context, String medicalId) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().orderCancel(medicalId)
                .enqueue(new Callback<GeneralModel>() {
                    @Override
                    public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                        getView().enableLoadingBar(context, false, "");
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onOrderCancelSuccess(response.body());

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