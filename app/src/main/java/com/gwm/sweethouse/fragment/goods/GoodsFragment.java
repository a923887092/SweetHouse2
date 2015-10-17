package com.gwm.sweethouse.fragment.goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.gwm.sweethouse.GoodsActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.SearchActivity;
import com.gwm.sweethouse.bean.SubClass;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.fragment.mall.WyFragment;
import com.gwm.sweethouse.fragment.mall.ZmFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.protocol.MallZmProtocol;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.view.NoFullScreenViewPager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/15.
 */
public class GoodsFragment extends BaseFragment {


    private ImageView ivBack;
    private ArrayList<ArrayList<SubClass>> subClasses = new ArrayList<>();
    private FragmentCallBack mFragmentCallBack;

    public GoodsFragment() {
        super(R.layout.pager_goods);
        /*Bundle arguments = getArguments();
        sortId = arguments.getInt("xl");*/
//        sortId = (int) getActivity().getIntent().getSerializableExtra("xl");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentCallBack = (GoodsActivity) activity;
    }

    @Override
    protected void initFragment(View view) {
        ivBack = (ImageView) view.findViewById(R.id.iv_goods_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected LoadResult load() {
       /* int a = mFragmentCallBack.callbackFun(null);
        System.out.println("++++++++++++++_______-" + a);
        MallZmProtocol protocol;
//            protocol = new MallZmProtocol(mUrls[i], mCacheDirs[i]);
//            LogUtils.i("mUrl:" + mUrls[1]);
//            subClasses.add(protocol.loadData());
        LogUtils.i("subClasses:" + subClasses);
        if (subClasses.get(0) == null){
            return LoadResult.error;
        } else {
            if (subClasses.get(0).size() == 0){
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }*/
        return null;
    }

    @Override
     protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_goods, null);
        return view;
    }
}
