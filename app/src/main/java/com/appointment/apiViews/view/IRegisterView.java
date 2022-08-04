package com.appointment.apiViews.view;

import com.appointment.apiViews.model.CityModel;
import com.appointment.apiViews.model.SignUpModel;
import com.appointment.apiViews.model.StateModel;

public interface IRegisterView extends IRootView {
    public  void onSuccess(SignUpModel item);
    void onStateSuccess(StateModel item);
    void onCitySuccess(CityModel item);
}
