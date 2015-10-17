package com.gwm.sweethouse.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.LoginActivity;
import com.gwm.sweethouse.MyAddressActivity;
import com.gwm.sweethouse.MyAdviceActivity;
import com.gwm.sweethouse.MyOrderActivity;
import com.gwm.sweethouse.MyWalletActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.MyOptionAdapter;
import com.gwm.sweethouse.bean.MyOptionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/5.
 */
public class MyFragment extends Fragment implements AdapterView.OnItemClickListener {

    List<MyOptionData> list = new ArrayList<MyOptionData>();

    private Button loginbutton;
   // private ImageButton userImage;
    private ListView listview;
    private TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_my, container, false);
        initViews(view);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        getData();
        listview.setAdapter(new MyOptionAdapter(list, getActivity()));
        listview.setOnItemClickListener(this);
        return view;
    }

    private void initViews(View view) {
        loginbutton = (Button) view.findViewById(R.id.btn_login);
        //userImage = (ImageButton) view.findViewById(R.id.iv_userImage);
        textView = (TextView) view.findViewById(R.id.tv_welcome);
        listview = (ListView) view.findViewById(R.id.lv_myItem);
    }

    private boolean isLogined() {
        //如果用户未登录，该imageview隐藏，登陆后不隐藏
      //  userImage.setVisibility(View.INVISIBLE);
        //登陆后将textview内容设为用户手机号
        textView.setText("用户手机号码");
        return true;
    }

    private void getData() {
        MyOptionData d1 = new MyOptionData(R.drawable.ic_my_wallet, "我的钱包");
        MyOptionData d2 = new MyOptionData(R.drawable.ic_my_order, "我的订单");
        MyOptionData d3 = new MyOptionData(R.drawable.ic_my_addr, "收货地址");
        MyOptionData d4 = new MyOptionData(R.drawable.ic_my_advice, "意见反馈");
        MyOptionData d5 = new MyOptionData(R.drawable.ic_my_us, "清除缓存");

        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        list.add(d5);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position){
            case 0 :
                startActivity(new Intent(getActivity(), MyWalletActivity.class));
                break;
            case 1 :
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
                break;
            case 2 :
                startActivity(new Intent(getActivity(), MyAddressActivity.class));
                break;
            case 3 :
                startActivity(new Intent(getActivity(), MyAdviceActivity.class));
                break;
            case 4 :
                Toast.makeText(getActivity(),"缓存已清除",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
