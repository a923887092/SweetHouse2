package com.gwm.sweethouse;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gwm.sweethouse.fragment.saled.SaledFragment;
import com.gwm.sweethouse.interfaces.FragmentCallBack;

public class SaleActivity extends FragmentActivity implements FragmentCallBack{

    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        state = getIntent().getIntExtra("state", 0);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new SaledFragment()).commit();
    }

    @Override
    public int callbackFun(Bundle arg) {
        return state;
    }
}
