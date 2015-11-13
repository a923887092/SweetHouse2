package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwm.sweethouse.AddressSelectActivity;
import com.gwm.sweethouse.OrderConfirmationActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.AdressBean;
import com.umeng.message.proguard.A;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/3.
 */
public class AddressSelectAdapter extends BaseAdapter{
    List<AdressBean> addresslist=new ArrayList<AdressBean>();
    ViewHolder viewHolder;
    LayoutInflater inflater;
    Context context;
    @Override
    public int getCount() {
        return addresslist.size();
    }

    @Override
    public Object getItem(int position) {
        return addresslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public AddressSelectAdapter(List<AdressBean> addresslist,Context context) {
        this.addresslist = addresslist;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_address_select,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_address_detail= (TextView) convertView.findViewById(R.id.address_detail);
            viewHolder.tv_address_name= (TextView) convertView.findViewById(R.id.address_name);
            viewHolder.tv_address_phone= (TextView) convertView.findViewById(R.id.address_phone);
            viewHolder.tv_address_state= (TextView) convertView.findViewById(R.id.address_state);
            viewHolder.iv_address_temp_state= (ImageView) convertView.findViewById(R.id.address_temp_state);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (addresslist.get(position).getAdd_state()==1){
            viewHolder.tv_address_state.setVisibility(View.VISIBLE);
            viewHolder.iv_address_temp_state.setVisibility(View.VISIBLE);
        }
        viewHolder.tv_address_phone.setText(addresslist.get(position).getAdd_phone());
        viewHolder.tv_address_name.setText(addresslist.get(position).getAdd_name());
        viewHolder.tv_address_detail.setText(addresslist.get(position).getAdd_address());

        return convertView;
    }

    private class ViewHolder {
        TextView tv_address_name,tv_address_phone,tv_address_state,tv_address_detail;
        ImageView iv_address_temp_state;
    }
}
