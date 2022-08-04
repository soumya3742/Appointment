package com.appointment.apiViews.view;

import com.appointment.apiViews.model.DoctorListModel;

public interface IMyDoctorView extends IRootView {
    void onSuccess(DoctorListModel item);
}