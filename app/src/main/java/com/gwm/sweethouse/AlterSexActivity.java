package com.gwm.sweethouse;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class AlterSexActivity extends Activity {
    ImageButton ibtn_sexTouser;
    RelativeLayout rl_sexMan,rl_sexWomen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_sex);

        initViews();
    }

    private void initViews() {
        ibtn_sexTouser = (ImageButton) findViewById(R.id.ibtn_sexToUser);
        rl_sexMan = (RelativeLayout) findViewById(R.id.rl_sexMan);
        rl_sexWomen = (RelativeLayout) findViewById(R.id.rl_sexWomen);

        ibtn_sexTouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rl_sexMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rl_sexWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}
