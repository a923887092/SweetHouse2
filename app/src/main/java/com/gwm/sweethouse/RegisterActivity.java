package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends Activity implements View.OnClickListener {
    CheckBox checkBox;
    EditText phonEditText, verEditText;
    Button sensmsButton,verificationButton;
    ImageButton returnLogin;
    private String phString;
    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "b00d2dc3c57f";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "35935d9347caacbe6b7e2bfa21021358";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        //启动SDK
        SMSSDK.initSDK(RegisterActivity.this, APPKEY, APPSECRET);
        SMSSDK.registerEventHandler(eh); //注册短信回调
        handler=new Handler(){

            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        Intent intent1 = new Intent(RegisterActivity.this, RegisterActivity2.class);
                        //将手机号码携带到密码设置界面，结合密码，添加到数据库
                        intent1.putExtra("phoneNum",phString);
                        Log.e("校验验证码", phString);
                        startActivity(intent1);
                            break;
                    default:
                        break;
                }
            }

        };
    }



    private void initViews() {
        checkBox = (CheckBox) findViewById(R.id.cb_agree);
        checkBox.setChecked(true);
        phonEditText = (EditText) findViewById(R.id.et_phonenumber);
        verEditText = (EditText) findViewById(R.id.et_identCode);
        sensmsButton = (Button) findViewById(R.id.btn_getCode);
        verificationButton = (Button) findViewById(R.id.btn_next);
        returnLogin = (ImageButton) findViewById(R.id.ibtn_return);

        sensmsButton.setOnClickListener(this);
        verificationButton.setOnClickListener(this);
        returnLogin.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_return:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_getCode://获取验证码
                phString=phonEditText.getText().toString();
                if(!TextUtils.isEmpty(phString)){
                    //getVerificationCode用于向服务器请求发送验证码的服务，需要传递国家代号和接收验证码的手机号码，
                    //请求getVerificationCode的时间间隔不应该小于60秒，否则服务端会返回“操作过于频繁”的错误
                    SMSSDK.getVerificationCode("86",phString);
                    Toast.makeText(this, "验证码已发送", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_next://校验验证码
                if(!TextUtils.isEmpty(verEditText.getText().toString())) {

                    SMSSDK.submitVerificationCode("86", phString, verEditText.getText().toString());
                    //需要判断验证码是否正确，若正确携带验证码跳转到设置密码界面，否者不进行跳转，重新获取
                }else {
                    Toast.makeText(this, "请进行验证",  Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.e("event", "提交验证码成功");
                    Message message=new Message();
                    message.what=0;
                    message.obj="提交验证码成功";
                    handler.sendMessage(message);

                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    Log.e("event", "获取验证码成功");

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
