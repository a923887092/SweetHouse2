package com.gwm.sweethouse.base.impl;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.base.BasePager;

/**
 * Created by Administrator on 2015/9/23.
 */
public class HomePager extends BasePager {


    private ViewPager vpNews;
    public HomePager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        View mView = View.inflate(mActivity, R.layout.pager_home, null);

        return mView;
    }

    @Override
    public void initData() {

    }
}
