package com.gwm.sweethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.gwm.sweethouse.fragment.company.DetailFragment;
import com.gwm.sweethouse.interfaces.FragmentCallBack;

public class DetailCompanyActivity extends FragmentActivity implements FragmentCallBack{
    private int company_id;

    //绑定fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_company);
        //获取床送过来的数据--点击需要加载哪些数据
        Intent intent = getIntent();
        company_id = intent.getIntExtra("company_id", 0);
        Log.e("gg",company_id+"");
        //绑定fragment界面
        DetailFragment fragment= new DetailFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.fl_content,fragment);
        transaction.commit();

    }

    @Override
    public int callbackFun(Bundle arg) {
        return company_id;
    }
}
