package com.appointment.apiViews.presenter;

import android.app.Activity;

import com.appointment.R;
import com.appointment.RootApp;
import com.appointment.apiViews.model.DoctorDetails;
import com.appointment.apiViews.model.DoctorModel;
import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.view.IDoctorView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPresenter extends BasePresenter<IDoctorView> {

    public void getDoctorList(final Activity context, Integer id) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getDoctorList(id)
                .enqueue(new Callback<DoctorModel>() {
                    @Override
                    public void onResponse(Call<DoctorModel> call, Response<DoctorModel> response) {
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
                    public void onFailure(Call<DoctorModel> call, Throwable t) {
                        getView().enableLoadingBar(context, false, "");
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }

    public void getDoctorSearchList(final Activity context, String searchKey ,  int catId) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getDoctorSearchList(catId , searchKey)
                .enqueue(new Callback<DoctorModel>() {
                    @Override
                    public void onResponse(Call<DoctorModel> call, Response<DoctorModel> response) {
                        getView().enableLoadingBar(context, false, "");
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSuccessSearch(response.body());

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
                    public void onFailure(Call<DoctorModel> call, Throwable t) {
                        getView().enableLoadingBar(context, false, "");
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }

    public void getDoctorDetails(final Activity context, Integer doctorid) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getDoctorDetails(doctorid)
                .enqueue(new Callback<DoctorDetails>() {
                    @Override
                    public void onResponse(Call<DoctorDetails> call, Response<DoctorDetails> response) {
                        getView().enableLoadingBar(context, false, "");
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSuccessDetails(response.body());

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
                    public void onFailure(Call<DoctorDetails> call, Throwable t) {
                        getView().enableLoadingBar(context, false, "");
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }

    public void drAppointment(final Activity context, Integer doctorid, int userId, String timeing,
                              String typeId, String patientName, String patientPhone, String patientEmail, String patientGender, String patientDob, String currentDate, String fees) {
        getView().enableLoadingBar(context, true, context.getString(R.string.loading));

        RootApp.getInstance().getApiService().getDoctoronAppointmnet(doctorid, userId, timeing , typeId , patientName , patientPhone , patientEmail , patientGender , patientDob , currentDate , fees)
                .enqueue(new Callback<GeneralModel>() {
                    @Override
                    public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                        getView().enableLoadingBar(context, false, "");
                        try {
                            if (!handleError(response, context)) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    assert response.body() != null;
                                    getView().onSuccessAppointmnet(response.body());

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
                        getView().enableLoadingBar(context, false, "");
                        t.printStackTrace();
                        getView().onError(null);
                    }
                });
    }
}