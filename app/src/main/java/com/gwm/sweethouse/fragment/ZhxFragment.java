package com.gwm.sweethouse.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gwm.sweethouse.BeautifulPicActivity;
import com.gwm.sweethouse.CalculatorActivity;
import com.gwm.sweethouse.CalenderActivity;
import com.gwm.sweethouse.GetCompanyActivity;
import com.gwm.sweethouse.GetRoomActivity;
import com.gwm.sweethouse.MallActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.calender.CalendarActivity;

/**
 * Created by Administrator on 2015/10/6.
 */
public class ZhxFragment extends Fragment {
    private LinearLayout llRoom;
    private LinearLayout llCompany;
    private RelativeLayout rlCalender,rlGetPic,rlCalculator,rlCall, rlBuy;
    private View view;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = inflater.getContext();
        view = inflater.inflate(R.layout.pager_zhuangx,container,false);
        llRoom= (LinearLayout) view.findViewById(R.id.ll_room);
        llCompany= (LinearLayout) view.findViewById(R.id.ll_company);
        rlCalender= (RelativeLayout) view.findViewById(R.id.rl_huangli);
        rlGetPic= (RelativeLayout) view.findViewById(R.id.rl_getpic);
        rlCalculator= (RelativeLayout) view.findViewById(R.id.rl_calculator);
        rlCall= (RelativeLayout) view.findViewById(R.id.rl_kefu);
        rlBuy = (RelativeLayout) view.findViewById(R.id.rl_buy);
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
        rlBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MallActivity.class));
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
        //看图装修
        rlGetPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), BeautifulPicActivity.class);
                startActivity(intent);
            }
        });
        //建材计算器
        rlCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),CalculatorActivity.class);
                startActivity(intent);
            }
        });

        //
        rlCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
        });
        rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(getActivity())
                        .setMessage("18662160957")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18662160957"));
                                startActivity(intent);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        return view;
    }

}
