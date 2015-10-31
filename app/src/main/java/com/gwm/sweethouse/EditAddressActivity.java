package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EditAddressActivity extends Activity implements View.OnClickListener{
    Intent intent;
    int add_id;
    String add_name;
    String add_phone;
    String add_address;
    ImageButton ibtn_toMyAddress;
    TextView tv_confirmEdit,tv_editDistinct;
    EditText et_editName,et_editPhone,et_editDetailAdd;
    RelativeLayout rl_editDistict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        getIntentData();
        initViews();

    }

    private void initViews() {
        ibtn_toMyAddress = (ImageButton) findViewById(R.id.ibtn_toMyAddress);
        tv_confirmEdit = (TextView) findViewById(R.id.tv_confirmEdit);
        tv_editDistinct = (TextView) findViewById(R.id.tv_editDistinct);
        et_editName = (EditText) findViewById(R.id.et_editName);
        et_editPhone = (EditText) findViewById(R.id.et_editPhone);
        et_editDetailAdd = (EditText) findViewById(R.id.et_editDetailAdd);
        rl_editDistict = (RelativeLayout) findViewById(R.id.rl_editDistict);

        et_editName.setText(add_name);
        et_editPhone.setText(add_phone);
        et_editName.setText(add_name);
        et_editDetailAdd.setText(add_address);
        ibtn_toMyAddress.setOnClickListener(this);
        tv_confirmEdit.setOnClickListener(this);

    }

    private void getIntentData() {
        intent = getIntent();
        add_id = intent.getIntExtra("add_id", 0);
        add_name = intent.getStringExtra("add_name");
        add_phone = intent.getStringExtra("add_phone");
        add_address = intent.getStringExtra("add_address");
        Log.e("intent",add_id+","+add_name+","+add_phone+","+add_address);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibtn_toMyAddress:
                finish();
            case R.id.tv_confirmEdit:


        }
    }
}
