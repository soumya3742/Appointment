package com.appointment.apiViews.view;


import com.appointment.apiViews.model.CityModel;
import com.appointment.apiViews.model.ProfileModel;
import com.appointment.apiViews.model.StateModel;

public interface IProfileView extends IRootView {
    void onSuccess(ProfileModel category);
    void onStateSuccess(StateModel item);
    void onCitySuccess(CityModel item);
}
