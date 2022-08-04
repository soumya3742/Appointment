package com.appointment.apiViews.view;

import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.model.OrderList;

public interface IMyOrderView extends IRootView {
    void onSuccess(OrderList item);
    void onOrderCancelSuccess(GeneralModel item);
}