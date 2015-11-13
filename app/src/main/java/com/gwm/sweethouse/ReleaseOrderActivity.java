package com.gwm.sweethouse;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.message.PushAgent;

public class ReleaseOrderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_order);
        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(ReleaseOrderActivity.this).onAppStart();
    }


}
