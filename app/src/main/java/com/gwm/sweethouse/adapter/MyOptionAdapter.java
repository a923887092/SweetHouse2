package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.MyOptionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/16.
 */
public class MyOptionAdapter extends BaseAdapter {
    List<MyOptionData> list = new ArrayList<MyOptionData>();
    Context context;
    LayoutInflater mInflater;

    public MyOptionAdapter(List<MyOptionData> list, Context context) {
        super();
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        OptionViewHolder holder;
        if (convertView == null){
            holder = new OptionViewHolder();
            convertView = View.inflate(context, R.layout.item_list_myoption,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_item);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (OptionViewHolder) convertView.getTag();
        }
        int resId = list.get(position).getPicId();
        holder.imageView.setImageResource(resId);
        String text = list.get(position).getText();
        holder.textView.setText(text);

        return convertView;
    }

    class OptionViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
