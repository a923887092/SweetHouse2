package com.gwm.sweethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.gwm.sweethouse.fragment.groupbuy.GroupBuyFragment;
import com.gwm.sweethouse.interfaces.FragmentCallBackString;

public class GroupBuyActivity extends FragmentActivity implements FragmentCallBackString {

    private String area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_buy);
        Intent intent = getIntent();
        area = intent.getStringExtra("area");
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new GroupBuyFragment()).commit();
    }

    @Override
    public String callback(Bundle arg) {
        return area;
    }
}
