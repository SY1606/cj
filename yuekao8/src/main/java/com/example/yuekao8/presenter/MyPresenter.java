package com.example.yuekao8.presenter;

import com.example.yuekao8.bean.Result;
import com.example.yuekao8.core.BasePresenter;
import com.example.yuekao8.core.DataCall;
import com.example.yuekao8.model.GoodsListModel;

public class MyPresenter extends BasePresenter {

    private int page=1;
    private boolean isRefresh = true;

    public MyPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Result getData(Object[] args) {
        isRefresh = (boolean) args[0];
        if (isRefresh){
            page = 1;
        }else {
            page++;
        }
        Result result = GoodsListModel.goodsList((String)args[1],page+"");
        return result;
    }
   public boolean isRefresh(){
        return isRefresh;
    }

}
