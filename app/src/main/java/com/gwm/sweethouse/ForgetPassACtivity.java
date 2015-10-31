package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ForgetPassACtivity extends Activity {
   ImageButton ibtn_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_activity);
        initViews();
    }

    private void initViews() {
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_forgetToLogin);
        ibtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPassACtivity.this,LoginActivity.class));
            }
        });
    }


}
