package com.appointment.apiViews.view;

import com.appointment.apiViews.model.DoctorDetails;
import com.appointment.apiViews.model.DoctorModel;
import com.appointment.apiViews.model.GeneralModel;

public interface IDoctorView extends IRootView {
    void onSuccess(DoctorModel item);
    void onSuccessSearch(DoctorModel item);
    void onSuccessDetails(DoctorDetails item);
    void onSuccessAppointmnet(GeneralModel item);
}