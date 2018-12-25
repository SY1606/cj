package com.example.yuekao6.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.yuekao6.R;
import com.example.yuekao6.bean.Goods;
import com.example.yuekao6.core.DTApplication;
import com.example.yuekao6.util.view.AddSubLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingtao
 * @date 2018/12/18 15:03
 * qq:1940870847
 */
public class CartAdapter extends BaseAdapter {

    private List<Goods> mList = new ArrayList<>();

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(List<Goods> goods) {
        if (goods != null)
            mList.addAll(goods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder holder;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.cart_item, null);
            holder = new MyHolder();
            holder.text = convertView.findViewById(R.id.text);
            holder.image = convertView.findViewById(R.id.image);
            holder.addSub = convertView.findViewById(R.id.add_sub_layout);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        Goods goods = mList.get(position);
        holder.text.setText(goods.getTitle());
        String imageurl = "https" + goods.getImages().split("https")[1];
        Log.i("dt", "imageUrl: " + imageurl);
        imageurl = imageurl.substring(0, imageurl.lastIndexOf(".jpg") + ".jpg".length());
        Glide.with(DTApplication.getInstance()).load(imageurl).into(holder.image);//加载图片

        holder.addSub.setCount(goods.getCount());
        return convertView;
    }

    class MyHolder {
        TextView text;
        ImageView image;
        AddSubLayout addSub;
    }
}
