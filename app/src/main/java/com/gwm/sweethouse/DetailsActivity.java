package com.gwm.sweethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.gwm.sweethouse.fragment.details.DetailsFragment;
import com.gwm.sweethouse.fragment.details.RightFragment;
import com.gwm.sweethouse.fragment.saled.SaledDetailsFragment;
import com.gwm.sweethouse.interfaces.DetailsFragmentCallBack;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.utils.LogUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class DetailsActivity extends SlidingFragmentActivity implements DetailsFragmentCallBack {

    private int goodsId;
    private int state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        setBehindContentView(R.layout.right_menu);
        Intent intent = getIntent();
        goodsId = (int) intent.getSerializableExtra("goodsId");
        state = intent.getIntExtra("state", -1);
        LogUtils.d("state:" + state);
        LogUtils.d("从上一个页面传来的数据为 ：" + goodsId + "++" + state);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DetailsFragment fragment = new DetailsFragment();
        if (state == 1){
            transaction.replace(R.id.fl_fragment, new SaledDetailsFragment());
        } else if (state == 0){
            transaction.replace(R.id.fl_fragment, new SaledDetailsFragment());
        } else if (state == -1){
            transaction.replace(R.id.fl_fragment, fragment);
        }
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
    public int callbackGoodsId(Bundle arg) {
            return goodsId;
    }

    @Override
    public int callbackState(Bundle arg) {
        return state;
    }
}
