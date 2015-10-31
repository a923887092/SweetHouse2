/*
 * Copyright (C) 2012 The TimeSale Project
 * All right reserved.
 * Version 1.00 2012-11-25
 * Author veally@foxmail.com
 */
package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.Saled;
import com.gwm.sweethouse.utils.CustomDigitalClock;
import com.lidroid.xutils.BitmapUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author veally@foxmail.com
 */
public class AdapterSaleItem extends BaseAdapter {

    private Context mContext;
    private List<Saled> mItems;
    private LayoutInflater inflater;

    private BitmapUtils utils;
    public AdapterSaleItem(Context context, List<Saled> items) {
        this.mContext = context;
        this.mItems = items;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class SaledViewHolder {
        ImageView ivImg;
        TextView tvTitle, tvPrice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SaledViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_saled_goods, null);
            holder = new SaledViewHolder();
            holder.ivImg = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        } else {
            holder = (SaledViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
