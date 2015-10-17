package com.gwm.sweethouse.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2015/9/23.
 */
public abstract class BasePager {
    public View mView;
    public Activity mActivity;

    public BasePager(Activity mActivity) {
        this.mActivity = mActivity;
        mView = initViews();
    }

    public abstract View initViews();


    public void initData(){

    }
}
