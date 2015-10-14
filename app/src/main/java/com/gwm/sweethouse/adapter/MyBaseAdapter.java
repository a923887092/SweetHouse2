package com.gwm.sweethouse.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/14.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected ArrayList<T> datas;

    public MyBaseAdapter(ArrayList<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViews(position, convertView, parent);
    }

    protected abstract View getViews(int position, View convertView, ViewGroup parent);

}
