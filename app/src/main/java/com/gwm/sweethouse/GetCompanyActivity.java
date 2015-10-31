package com.gwm.sweethouse;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gwm.sweethouse.fragment.company.CompanyFragment;

/**
 * Created by Administrator on 2015/10/23.
 */
//同城装修
public class GetCompanyActivity extends FragmentActivity{
    //同城装修--返回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        CompanyFragment fragment=new CompanyFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content, fragment);
        transaction.commit();

    }

}
