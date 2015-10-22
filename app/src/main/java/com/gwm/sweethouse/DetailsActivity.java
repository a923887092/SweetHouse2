package com.gwm.sweethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.gwm.sweethouse.fragment.details.DetailsFragment;
import com.gwm.sweethouse.fragment.details.RightFragment;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.utils.LogUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class DetailsActivity extends SlidingFragmentActivity implements FragmentCallBack{

    private int goodsId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        setBehindContentView(R.layout.right_menu);
        Intent intent = getIntent();
        goodsId = (int) intent.getSerializableExtra("goodsId");

        LogUtils.i("从上一个页面传来的数据为 ：" + goodsId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DetailsFragment fragment = new DetailsFragment();
        transaction.replace(R.id.fl_fragment, fragment);
        transaction.replace(R.id.fl_right_menu, new RightFragment());
        transaction.commit();
        SlidingMenu sm = getSlidingMenu();
        sm.setMode(SlidingMenu.RIGHT);
        sm.setShadowWidth(50);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffset(60);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        sm.setFadeDegree(0.35f);
    }

    @Override
    public int callbackFun(Bundle arg) {
        return goodsId;
    }
}
