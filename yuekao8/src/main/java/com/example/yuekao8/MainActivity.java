package com.example.yuekao8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yuekao8.adapter.GoodsListAdapter;
import com.example.yuekao8.animator.ScaleItemAnimator;
import com.example.yuekao8.bean.Goods;
import com.example.yuekao8.bean.Result;
import com.example.yuekao8.core.DataCall;
import com.example.yuekao8.presenter.MyPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements XRecyclerView.LoadingListener,
        DataCall<List<Goods>>,View.OnClickListener,GoodsListAdapter.OnItemClickListener,GoodsListAdapter.OnItemLongClickListener {

    private ImageView mBtnLayout;
    private EditText editText;
    private XRecyclerView xRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private GoodsListAdapter goodsListAdapter;
    private MyPresenter mPresenter = new MyPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_keywords);
        mBtnLayout = findViewById(R.id.btn_layout);

        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.shop_car).setOnClickListener(this);
        mBtnLayout.setOnClickListener(this);

        //这个是加载动画的

        xRecyclerView = findViewById(R.id.list_goods);
        xRecyclerView.setLoadingListener(this);

        gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        xRecyclerView.setLayoutManager(linearLayoutManager);

        goodsListAdapter = new GoodsListAdapter(this);
        goodsListAdapter.setOnItemClickListener(this);
        goodsListAdapter.setOnItemLongClickListener(this);
        xRecyclerView.setAdapter(goodsListAdapter);
    }



    @Override
    public void success(List<Goods> data) {
       xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
        if (mPresenter.isRefresh()){
            goodsListAdapter.clearList();
        }
        goodsListAdapter.addAll(data);
        goodsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(Result result) {
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
        Toast.makeText(this,result.getCode()+""+result.getMsg(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        String keywords = editText.getText().toString();
        mPresenter.requestData(true,keywords);
    }

    @Override
    public void onLoadMore() {
        String keywords = editText.getText().toString();
        mPresenter.requestData(false,keywords);
    }

    private boolean isGrid = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unBindCall();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_search){
            String keywords = editText.getText().toString();
            mPresenter.requestData(true,keywords);
        }else if(v.getId()==R.id.btn_layout){
            if (xRecyclerView.getLayoutManager().equals(linearLayoutManager)) {
                isGrid = true;
                goodsListAdapter.setViewType(GoodsListAdapter.GRID_TYPE);
                xRecyclerView.setLayoutManager(gridLayoutManager);
            } else {
                isGrid = false;
                goodsListAdapter.setViewType(GoodsListAdapter.LINEAR_TYPE);
                xRecyclerView.setLayoutManager(linearLayoutManager);
            }
            goodsListAdapter.notifyDataSetChanged();

        }else if(v.getId()==R.id.shop_car){
            Intent intent = new Intent(this,ShopActivity2.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(Goods goods) {
        Intent intent = new Intent(this,WebActivity.class);
        intent.putExtra("url",goods.getDetailUrl());
        startActivity(intent);
    }

    @Override
    public void OnItemLongClick(int position) {
           goodsListAdapter.remove(position);
           goodsListAdapter.notifyItemRemoved(position+1);
           if (position<goodsListAdapter.getItemCount()+1){
               goodsListAdapter.notifyItemRangeChanged(position+1,goodsListAdapter.getItemCount()-position);
           }
        }
}
