package com.gwm.sweethouse;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gwm.sweethouse.fragment.company.PicCodeDetailFragment;
import com.gwm.sweethouse.interfaces.FragmentCallBack;

public class CodePicDetailActivity extends FragmentActivity implements FragmentCallBack {
    private int codepic_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_pic_detail);
        Intent intent=getIntent();
        codepic_id=intent.getIntExtra("codepicid",0);
        PicCodeDetailFragment fragment=new PicCodeDetailFragment();
        android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.fl_content, fragment);
        transaction.commit();

    }

    @Override
    public int callbackFun(Bundle arg) {
        return codepic_id;
    }
}
