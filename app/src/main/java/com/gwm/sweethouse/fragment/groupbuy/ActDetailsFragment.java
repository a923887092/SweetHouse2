package com.gwm.sweethouse.fragment.groupbuy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.ActDetailsActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.MyActivity;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.ActDetailsFragmentCallBack;
import com.gwm.sweethouse.utils.LogUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2015/10/23.
 */
public class ActDetailsFragment extends BaseFragment {

    private ActDetailsFragmentCallBack callBack;
    private ImageView ivBack, ivImg;
    private MyActivity act;
    private TextView tvTitle, tvActTitle, tvDetailsAddr, tvPeopleNum;
    private BitmapUtils utils;
    private Button btnApply;
    private EditText etNumb;
    private AlertDialog.Builder alertDialog;

    public ActDetailsFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pager_act_details;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBack = (ActDetailsActivity) activity;
    }

    @Override
    protected void initFragment(View view) {
        ivBack = (ImageView) view.findViewById(R.id.iv_goods_back);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected LoadResult load() {
        act = callBack.callbackAct(null);
        LogUtils.d("load_act:" + act);
        return LoadResult.success;

    }

    @Override
    protected View createSuccessView() {
        utils = new BitmapUtils(getActivity());
        final View view = View.inflate(getActivity(), R.layout.fragment_act_details, null);
        tvTitle.setText(act.getAct_addr() + "团购活动");
        tvActTitle = (TextView) view.findViewById(R.id.tv_title);
        btnApply = (Button) view.findViewById(R.id.btn_apply);
        ivImg = (ImageView) view.findViewById(R.id.iv_img);
        tvPeopleNum = (TextView) view.findViewById(R.id.tv_people_num);
        tvDetailsAddr = (TextView) view.findViewById(R.id.tv_details_addr);
        utils.display(ivImg, GlobalContacts.SERVER_URL + act.getAct_img());
        tvActTitle.setText(act.getAct_title());
        tvDetailsAddr.setText(act.getAct_detail_addr());
        tvPeopleNum.setText(act.getAct_people_num() + "人已报名");
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogEditText = View.inflate(getActivity(), R.layout.dialog_edittext, null);
                etNumb = (EditText) dialogEditText.findViewById(R.id.editText);
                alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("马上报名，现场送好礼")//提示框标题
                        .setPositiveButton("确定",//提示框的两个按钮
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //事件
                                        String numb = etNumb.getText().toString();
                                        Toast.makeText(getActivity(), "报名成功", Toast.LENGTH_SHORT).show();
                                        /*((ViewGroup) dialogEditText.getParent()).removeView(dialogEditText);
                                        dialog.dismiss();*/
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setView(dialogEditText).create().show();

            }
        });
        return view;
    }
}
