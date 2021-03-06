package com.example.yuekao6.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuekao6.R;
import com.example.yuekao6.adapter.CartAdapter2;
import com.example.yuekao6.bean.Result;
import com.example.yuekao6.bean.Shop;
import com.example.yuekao6.core.DataCall;
import com.example.yuekao6.presenter.CartPresenter;

import java.util.List;


public class ShopCartActivity2 extends AppCompatActivity implements
        DataCall<List<Shop>>,CartAdapter2.TotalPriceListener {

    private TextView mSumPrice;
    private CheckBox mCheckAll;

    private ExpandableListView mGoodsList;
    private CartAdapter2 mCartAdapter;

    private CartPresenter cartPresenter = new CartPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);

        //先找到控件
        mSumPrice = findViewById(R.id.goods_sum_price);
        mCheckAll = findViewById(R.id.check_all);

        //点击全选框就选中了
        mCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCartAdapter.checkAll(isChecked);
            }
        });
        mGoodsList = findViewById(R.id.list_cart);
        mCartAdapter = new CartAdapter2();
        mGoodsList.setAdapter(mCartAdapter);
        mCartAdapter.setTotalPriceListener(this);//设置总价回调器
        mGoodsList.setGroupIndicator(null);
        //让其group不能被点击
        mGoodsList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return true;
            }
        });
        cartPresenter.requestData();
    }

    @Override
    public void success(List<Shop> data) {
       mCartAdapter.addAll(data);
        //遍历所有group,将所有项设置成默认展开
        int groupCount = data.size();
        for (int i = 0; i < groupCount; i++) {
            mGoodsList.expandGroup(i);
        }

        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(Result result) {
        Toast.makeText(this, result.getCode() + "   " + result.getMsg(), Toast.LENGTH_LONG).show();
    }

    //这个是算价钱的
    @Override
    public void totalPrice(double totalPrice) {
        mSumPrice.setText(String.valueOf(totalPrice));
    }
}
