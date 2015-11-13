package com.gwm.sweethouse;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.message.PushAgent;

public class ApplyReturnActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_return);

        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(ApplyReturnActivity.this).onAppStart();
    }


}
