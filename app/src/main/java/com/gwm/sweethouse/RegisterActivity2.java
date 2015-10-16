package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


public class RegisterActivity2 extends Activity {
    TextView testTextView1, testTextView2;
    EditText editText1, editText2;
    String firstPass, secondPass;
    Intent intent1;
    String phoneNumber;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        editText1 = (EditText) findViewById(R.id.et_remind1);
        editText2 = (EditText) findViewById(R.id.et_remind2);
        intent1 = getIntent();
    }

    public void toRegister(View view) {
        Intent intent = new Intent(RegisterActivity2.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void commitRegister(View view) {
        if (firstPass == "") {
            Toast.makeText(RegisterActivity2.this, "初始密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (secondPass == "") {
            Toast.makeText(RegisterActivity2.this, "请再次输入初始密码", Toast.LENGTH_SHORT).show();
        } else {
            phoneNumber = intent1.getStringExtra("phoneNum");
            password = editText1.getText().toString();
            //已经能够Toast显示用户名和密码，
            addUser(phoneNumber, password);

            Toast.makeText(this, phoneNumber + password, Toast.LENGTH_SHORT).show();

        }
    }

    //将用户名和密码添加到数据库中
    public void addUser(String phoneNumber, String password) {
        Log.e("信息验证",phoneNumber+"::"+password);
        HttpUtils http = new HttpUtils();
        String url= GlobalContacts.VISON_URL+"/userServlet?method=addUser&phoneNumber="
                            +phoneNumber+"&password="+password;
        http.send(HttpRequest.HttpMethod.GET,
                  url,
                  new RequestCallBack<String>() {
                      @Override
                      public void onSuccess(ResponseInfo<String> responseInfo) {

                          Toast.makeText(RegisterActivity2.this, "注册成功！", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(RegisterActivity2.this, MainActivity.class);
                          startActivity(intent);
                      }
                      @Override
                      public void onFailure(HttpException e, String s) {
                          Toast.makeText(RegisterActivity2.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
                      }
                  }
           );

    }
}
