package com.gwm.sweethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.gwm.sweethouse.fragment.goods.GoodsFragment;
import com.gwm.sweethouse.interfaces.FragmentCallBack;

public class GoodsActivity extends FragmentActivity implements FragmentCallBack {
    public int subClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods);
        Intent intent = getIntent();
        subClass = (int) intent.getSerializableExtra("xl");
        Bundle bundle = new Bundle();
        bundle.putInt("xl", subClass);
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_fragment, fragment);
        transaction.commit();
        System.out.println("+++++++++++++" + subClass);
    }

    @Override
    public int callbackFun(Bundle arg) {
        return subClass;
    }
}
