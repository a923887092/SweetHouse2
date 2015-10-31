package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gwm.sweethouse.fragment.search.ResultFragment;
import com.gwm.sweethouse.interfaces.FragmentCallBackString;
import com.gwm.sweethouse.utils.LogUtils;

public class ResultActivity extends FragmentActivity implements FragmentCallBackString {

    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        LogUtils.d("搜索的内容为：" + content);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new ResultFragment()).commit();

    }

    @Override
    public String callback(Bundle arg) {
        return content;
    }
}
