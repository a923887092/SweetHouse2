package com.gwm.sweethouse.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gwm.sweethouse.CalenderActivity;
import com.gwm.sweethouse.GetCompanyActivity;
import com.gwm.sweethouse.GetRoomActivity;
import com.gwm.sweethouse.R;

/**
 * Created by Administrator on 2015/10/6.
 */
public class ZhxFragment extends Fragment {
    private LinearLayout llRoom;
    private LinearLayout llCompany;
    private RelativeLayout rlCalender;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pager_zhuangx,container,false);
        llRoom= (LinearLayout) view.findViewById(R.id.ll_room);
        llCompany= (LinearLayout) view.findViewById(R.id.ll_company);
        rlCalender= (RelativeLayout) view.findViewById(R.id.rl_huangli);
        //量房设计
        llRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                //fragment获取context---getActivity（）
                intent = new Intent(getActivity(), GetRoomActivity.class);
                startActivity(intent);
            }

        });
        //同城装修公司
        llCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                //fragment获取context---getActivity（）
                intent = new Intent(getActivity(), GetCompanyActivity.class);
                startActivity(intent);

            }
        });
        rlCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CalenderActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
