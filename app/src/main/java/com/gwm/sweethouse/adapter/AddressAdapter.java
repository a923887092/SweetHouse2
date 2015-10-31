package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.EditAddressActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/25.
 */
public class AddressAdapter extends BaseAdapter {
    List<Address> list = new ArrayList<Address>();
    Context context;
    LayoutInflater inflater;
    AddviewHolder viewHolder;


    public AddressAdapter(Context context, List<Address> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = inflater.inflate(R.layout.item_list_address,null);
            viewHolder = new AddviewHolder();

            viewHolder.tv_addName = (TextView) view.findViewById(R.id.tv_addName);
            viewHolder.tv_addPhone = (TextView) view.findViewById(R.id.tv_addPhone);
            viewHolder.tv_addAddress = (TextView) view.findViewById(R.id.tv_addAddress);
            viewHolder.rl_editAdd = (RelativeLayout) view.findViewById(R.id.rl_editAdd);
            viewHolder.rl_deleteAdd = (RelativeLayout) view.findViewById(R.id.rl_deleteAdd);
            view.setTag(viewHolder);
        }else{
            viewHolder = (AddviewHolder)view.getTag();
        }

        viewHolder.tv_addName.setText(list.get(i).getAdd_name());
        viewHolder.tv_addPhone.setText(list.get(i).getAdd_phone());
        viewHolder.tv_addAddress.setText(list.get(i).getAdd_address());
        final Address address = list.get(i);
        viewHolder.rl_editAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "修改订单", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,EditAddressActivity.class);
                intent.putExtra("add_name",address.getAdd_name());
                intent.putExtra("add_id",address.getAdd_id());
                intent.putExtra("add_phone",address.getAdd_phone());
                intent.putExtra("add_address",address.getAdd_address());
                context.startActivity(intent);
            }
        });
        viewHolder.rl_deleteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "删除订单", Toast.LENGTH_SHORT).show();
            }
        });
        return view ;
    }

    private class AddviewHolder{
       private TextView tv_addName,tv_addPhone,tv_addAddress;
        private RelativeLayout rl_editAdd, rl_deleteAdd;
    }

}
