package com.example.yuekao8.presenter;

import com.example.yuekao8.bean.Result;
import com.example.yuekao8.core.BasePresenter;
import com.example.yuekao8.core.DataCall;
import com.example.yuekao8.model.CartModel;

public class CartPresenter extends BasePresenter {

    public CartPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Result getData(Object... args) {
        Result result = CartModel.goodsList();
        return result;
    }
}
