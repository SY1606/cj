package com.example.yuekao6.presenter;


import com.example.yuekao6.bean.Result;
import com.example.yuekao6.core.BasePresenter;
import com.example.yuekao6.core.DataCall;
import com.example.yuekao6.model.GoodsListModel;

public class GoodsListPresenter extends BasePresenter {

    private int page=1;
    private boolean isRefresh=true;

    public GoodsListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Result getData(Object... args) {
        isRefresh = (boolean) args[0];//是否需要刷新
        if (isRefresh){//刷新
            page = 1;
        }else{
            page++;
        }
        Result result = GoodsListModel.goodsList((String)args[1],page+"");//调用网络请求获取数据
        return result;
    }

    public boolean isResresh(){
        return isRefresh;
    }
}
