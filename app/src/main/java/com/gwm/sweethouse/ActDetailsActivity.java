package com.gwm.sweethouse;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gwm.sweethouse.bean.MyActivity;
import com.gwm.sweethouse.fragment.groupbuy.ActDetailsFragment;
import com.gwm.sweethouse.interfaces.ActDetailsFragmentCallBack;

import java.io.Serializable;

public class ActDetailsActivity extends FragmentActivity implements ActDetailsFragmentCallBack{
    private MyActivity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_details);
        Intent intent = getIntent();
        act = (MyActivity) intent.getSerializableExtra("act");
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new ActDetailsFragment()).commit();
    }

    @Override
    public MyActivity callbackAct(Bundle arg) {
        return act;
    }
}
