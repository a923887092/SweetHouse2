package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.EditAddressActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.Address;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LoginUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

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
    SharedPreferences preferences;
    Handler handler;
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
            viewHolder.cb_defaultAdd = (CheckBox) view.findViewById(R.id.cb_defaultAdd);
            view.setTag(viewHolder);
        }else{
            viewHolder = (AddviewHolder)view.getTag();
        }
        final Address address = list.get(i);

        viewHolder.tv_addName.setText(address.getAdd_name());
        viewHolder.tv_addPhone.setText(address.getAdd_phone());
        viewHolder.tv_addAddress.setText(address.getAdd_address());

        if (address.getAdd_state() == 0){
            viewHolder.cb_defaultAdd.setChecked(false);
        }else {
            viewHolder.cb_defaultAdd.setChecked(true);
        }

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
                preferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);
                int user_id = preferences.getInt("user_id",0);
                String url = GlobalContacts.VISON_URL+"/AddressServlet?method="+"deleteAddress"+
                        "&user_id="+user_id+"&add_id="+list.get(i).getAdd_id();
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<Object>() {
                    @Override
                    public void onSuccess(ResponseInfo<Object> responseInfo) {
                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(context, "删除失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        viewHolder.cb_defaultAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    //将所有item的add_state设为0
                    //将该item的add_state设为1,刷新界面，重新查询
                    //查询时按照add_state降序排列
                    setDefault(address);
                    Toast.makeText(context, "选中了", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "取消选中", Toast.LENGTH_SHORT).show();
                    //将所有item的add_state设为0
                }
            }
        });

        return view ;
    }




    //将所有item的add_state设为0
    //将该item的add_state设为1,刷新界面，重新查询
    //查询时按照add_state降序排列
    private void setDefault(final Address address) {
       // http://localhost:8080/SweetHouse/AddressServlet?method=setDefault&user_id=123458&add_id=2
        HttpUtils httpUtils = new HttpUtils();
        int user_id = LoginUtils.getUser_id(context);
        String url = GlobalContacts.VISON_URL+"/AddressServlet";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("add_id",address.getAdd_id()+"");
        params.addQueryStringParameter("method","setDefault");
        params.addQueryStringParameter("user_id",user_id+"");

        httpUtils.send(HttpRequest.HttpMethod.POST, url,params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {

                list.remove(address);
                list.add(0,address);
                notifyDataSetChanged();
            }
            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    private class AddviewHolder{
        private TextView tv_addName,tv_addPhone,tv_addAddress;
        private RelativeLayout rl_editAdd, rl_deleteAdd;
        CheckBox cb_defaultAdd;
    }

}
