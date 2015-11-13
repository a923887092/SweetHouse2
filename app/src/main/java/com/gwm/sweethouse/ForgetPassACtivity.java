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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gwm.sweethouse.bean.User;
import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.message.PushAgent;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPassACtivity extends Activity implements View.OnClickListener{
   ImageButton ibtn_return;
    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "b00d2dc3c57f";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "35935d9347caacbe6b7e2bfa21021358";
    private Handler handler;
    EditText et_forgetPhone,et_identForgetCode;
    Button btn_getForgetCode,btn_nextForget,btn_goLogin;
    TextView tv_forgetPass;
    String phoneString;
    String vertiCode;
    LinearLayout ll_forgetLine1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_activity);
        initViews();
        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(ForgetPassACtivity.this).onAppStart();
        SMSSDK.initSDK(ForgetPassACtivity.this, APPKEY, APPSECRET);
        SMSSDK.registerEventHandler(eh); //注册短信回调
        handler=new Handler(){
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        ll_forgetLine1.setVisibility(View.GONE);
                        et_identForgetCode.setVisibility(View.GONE);
                        btn_nextForget.setVisibility(View.GONE);
                        getPass();
                        btn_goLogin.setVisibility(View.VISIBLE);
                        tv_forgetPass.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

        };
    }

    private void getPass() {
        HttpUtils http = new HttpUtils();
        String url= GlobalContacts.VISON_URL+"/userServlet?method=loginUser&phoneNumber="
                +phoneString;
        http.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Gson gson = new Gson();
                        User user = gson.fromJson(result, User.class);
                        String user_password = user.getUser_password();
                        Toast.makeText(ForgetPassACtivity.this,"密码获取成功！",Toast.LENGTH_SHORT).show();
                        tv_forgetPass.setText("原密码是："+user_password);
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(ForgetPassACtivity.this, "密码获取失败，请重试！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }

    private void initViews() {
        ll_forgetLine1 = (LinearLayout) findViewById(R.id.ll_forgetLine1);
        et_identForgetCode = (EditText) findViewById(R.id.et_identForgetCode);
        et_forgetPhone = (EditText) findViewById(R.id.et_forgetPhone);
        btn_getForgetCode = (Button) findViewById(R.id.btn_getForgetCode);
        btn_nextForget = (Button) findViewById(R.id.btn_nextForget);
        btn_goLogin = (Button) findViewById(R.id.btn_goLogin);
        btn_goLogin.setVisibility(View.INVISIBLE);
        tv_forgetPass = (TextView) findViewById(R.id.tv_forgetPass);
        tv_forgetPass.setVisibility(View.INVISIBLE);
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_forgetToLogin);

        btn_getForgetCode.setOnClickListener(this);
        btn_nextForget.setOnClickListener(this);
        btn_goLogin.setOnClickListener(this);
        ibtn_return.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        vertiCode = et_identForgetCode.getText().toString();
        phoneString = et_forgetPhone.getText().toString();
        switch (view.getId()){
            case R.id.btn_getForgetCode:

                if(!TextUtils.isEmpty(phoneString)){
                    //getVerificationCode用于向服务器请求发送验证码的服务，需要传递国家代号和接收验证码的手机号码，
                    //请求getVerificationCode的时间间隔不应该小于60秒，否则服务端会返回“操作过于频繁”的错误
                    SMSSDK.getVerificationCode("86",phoneString);
                    Toast.makeText(this, "验证码已发送" + phoneString, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_nextForget:
                if(!TextUtils.isEmpty(vertiCode)) {
                    Log.e(";;;;;", phoneString + vertiCode);
                    SMSSDK.submitVerificationCode("86", phoneString, vertiCode);
                    //需要判断验证码是否正确，若正确携带验证码跳转到设置密码界面，否者不进行跳转，重新获取
                }else {
                    Toast.makeText(this, "请进行验证",  Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_goLogin:
                startActivity(new Intent(ForgetPassACtivity.this,LoginActivity.class));
                break;

            case R.id.ibtn_return:
                finish();
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
}
