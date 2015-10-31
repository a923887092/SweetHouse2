package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AlterPassActivity extends Activity {
   ImageButton ibtn_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_pass);
        initViews();

    }

    private void initViews() {
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_returnUserinfo2);
        ibtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AlterPassActivity.this, UserInfoActivity.class));
            }
        });
    }

}
