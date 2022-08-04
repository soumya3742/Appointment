package com.appointment.apiViews.view;

import com.appointment.apiViews.model.LoginModel;

public interface ILoginView extends IRootView {
    void onLoginSucess(LoginModel item);
}
