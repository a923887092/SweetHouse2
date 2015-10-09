package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity {
    CheckBox checkBox;
    EditText phoneNumber, identCode;
    private String code;
    private String numberString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    private void initViews() {
        checkBox = (CheckBox) findViewById(R.id.cb_agree);
        checkBox.setChecked(true);
        phoneNumber = (EditText) findViewById(R.id.et_phonenumber);
        identCode = (EditText) findViewById(R.id.et_identCode);

    }


    public void returnLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    //获取验证码按钮的点击事件
    public void getCode(View view) {
        Toast.makeText(RegisterActivity.this, "已向该手机发送验证码", Toast.LENGTH_SHORT).show();

    }

    public void nextStep(View view) {
        validate();
        if (validate()) {
            Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
            intent.putExtra("phoneNumber",numberString);
            startActivity(intent);
        }
    }

    // 输入框空值验证方法
    public boolean validate() {
        code = identCode.getText().toString();
        numberString = phoneNumber.getText().toString();
        if (numberString.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入手机号验证", Toast.LENGTH_SHORT).show();
            return false;
        } else if (code.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入验证码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //向手机发送短信验证码
    private void sendCode(String number) {

    }

    //获取手机的短信验证码
    private String getIdentCode() {
        return " ";
    }
}
