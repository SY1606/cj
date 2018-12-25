package com.example.yuekao8.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuekao8.R;
import com.example.yuekao8.WebActivity;
import com.example.yuekao8.bean.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingtao
 * @date 2018/12/13 19:11
 * qq:1940870847
 */
public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.GoodsHodler> {

    private List<Goods> mList = new ArrayList<>();
    private Context context;


    public final static int LINEAR_TYPE = 0;//线性
    public final static int GRID_TYPE = 1;//网格

    private int viewType = LINEAR_TYPE;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public GoodsListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    //设置item的视图类型
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


    @NonNull
    @Override
    public GoodsHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == LINEAR_TYPE) {//通过第二个参数viewType判断选用的视图
            view = View.inflate(viewGroup.getContext(), R.layout.goods_linear_item, null);//加载item布局
        } else {
            view = View.inflate(viewGroup.getContext(), R.layout.goods_grid_item, null);//加载item布局
        }
        GoodsHodler goodsHodler = new GoodsHodler(view);

        return goodsHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsHodler goodsHodler, final int position) {
        final Goods goods = mList.get(position);//拿到商品，开始赋值

        goodsHodler.itemView.setTag(mList.get(position));

        //增加点击事件
        goodsHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebActivity.class);
                intent.putExtra("url",goods.getDetailUrl());
                context.startActivity(intent);
//                onItemClickListener.onItemClick(goods);
            }
        });

      goodsHodler.itemView.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
              if (onItemLongClickListener!=null){
                  onItemLongClickListener.OnItemLongClick(position);
              }
              return true;
          }
      });

        goodsHodler.text.setText(goods.getTitle());
        //由于我们的数据图片提供的不标准，所以我们需要切割得到图片
        String imageurl = "https" + goods.getImages().split("https")[1];
        Log.i("dt", "imageUrl: " + imageurl);
        imageurl = imageurl.substring(0, imageurl.lastIndexOf(".jpg") + ".jpg".length());
        Glide.with(context).load(imageurl).into(goodsHodler.imageView);//加载图片
    }

    public static void main(String[] args) {
        String aa = "a111a222a333a";
        String[] b = aa.split("a");
        System.out.println(b[0]);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 添加集合数据
     */
    public void addAll(List<Goods> data) {
        if (data != null) {
            mList.addAll(data);
        }
    }

    /**
     * 清空数据
     */
    public void clearList() {
        mList.clear();
    }

    /**
     * 设置点击方法
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void remove(int position){
        mList.remove(position);
    }

    class GoodsHodler extends RecyclerView.ViewHolder {
        TextView text;
        ImageView imageView;

        public GoodsHodler(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    /**
     * @author dingtao
     * @date 2018/12/15 10:28 AM
     * 点击接口
     */
    public interface OnItemClickListener {
        void onItemClick(Goods goods);
    }
    public interface OnItemLongClickListener{
        void OnItemLongClick(int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
