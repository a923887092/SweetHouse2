package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AlterUserNameActivity extends Activity {
    ImageButton ibtn_return;
    Intent intent;
    TextView tv_confirm;
    EditText et_newName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_user_name);
        initViews();
        //获取userinfo传递过来的数据
       // String nickName = intent.getStringExtra("nickName");
        et_newName.setText("nickname");
    }

    private void initViews() {
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_returnUserinfo);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        et_newName = (EditText) findViewById(R.id.et_newName);

        ibtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AlterUserNameActivity.this,UserInfoActivity.class));
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //返回UserInfoActivity时，携带设置的用户名信息
              startActivity(new Intent(AlterUserNameActivity.this,UserInfoActivity.class));

            }
        });

    }


}
