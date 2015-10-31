package com.gwm.sweethouse.fragment.saled;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.base.NewBaseFragment;
import com.gwm.sweethouse.bean.Saled;
import com.gwm.sweethouse.fragment.details.GoodImgFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.CustomDigitalClock;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/29.
 */
public class SaledFragment extends NewBaseFragment<Saled> {
    private static final String TAG = "GWM:SaledFragment";
    private ImageView ivBack;
    private View view;
    private ViewPager vpSaled;
    private ArrayList<Saled> saleds;
    private CustomDigitalClock remainTime;
    private TextView tvTitle;
    private View headerView;

    public SaledFragment() {
        super(R.layout.pager_saled);
    }

    @Override
    protected void initFragment(View view) {
        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected String getUrl() {
        return GlobalContacts.SALEDS_URL;
    }

    @Override
    protected ArrayList<Saled> parseJson(String result) {
        Gson gson = new Gson();
        saleds = gson.fromJson(result, new TypeToken<ArrayList<Saled>>() {
        }.getType());
//        LogUtils.d(TAG + saleds.toString());
        return saleds;
    }

    @Override
    protected View createSuccessView() {
        view = View.inflate(getActivity(), R.layout.fragment_saleds, null);
        vpSaled = (ViewPager) view.findViewById(R.id.vp_good);

        Bundle bundle = new Bundle();
        bundle.putSerializable("saleds", saleds);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("黄金八点档", EightFragment.class, bundle)
                .add("超值折扣", DiscountFragment.class, bundle)
                .create());
        vpSaled.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(vpSaled);
        return view;
    }




}
