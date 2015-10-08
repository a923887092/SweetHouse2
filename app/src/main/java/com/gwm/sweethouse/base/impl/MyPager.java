package com.gwm.sweethouse.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.gwm.sweethouse.base.BasePager;

/**
 * Created by Administrator on 2015/9/24.
 */
public class MyPager extends BasePager {

    public MyPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(mActivity);
        text.setText("我的");
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(25);
        return text;
    }
}
