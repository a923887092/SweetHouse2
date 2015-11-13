package com.gwm.sweethouse;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gwm.sweethouse.bean.User;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LoginUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.message.PushAgent;

public class AlterPassActivity extends Activity {
    ImageButton ibtn_return;
    LinearLayout ll_oldPass;
    EditText et_oldPass,et_newPass1,et_newPass2;
    Button btn_alterPass,btn_newPass;
    SharedPreferences preferences;
    String phoneNumber;
    String oldPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_pass);
        initViews();
        preferences = getSharedPreferences("login",AlterPassActivity.MODE_PRIVATE);
        phoneNumber = preferences.getString("phoneNumber", "");

        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(AlterPassActivity.this).onAppStart();
    }

    private void initViews() {
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_returnUserinfo2);
        ll_oldPass = (LinearLayout) findViewById(R.id.ll_oldPass);
        et_oldPass = (EditText) findViewById(R.id.et_oldPass);
        et_newPass1 = (EditText) findViewById(R.id.et_newPass1);
        et_newPass2 = (EditText) findViewById(R.id.et_newPass2);
        btn_alterPass = (Button) findViewById(R.id.btn_alterPass);
        btn_newPass = (Button) findViewById(R.id.btn_newPass);

        et_newPass1.setVisibility(View.INVISIBLE);
        et_newPass2.setVisibility(View.INVISIBLE);
        btn_alterPass.setVisibility(View.INVISIBLE);

        ibtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //判断et_oldPass是否为空，不为空，则去数据库验证，为空弹土司
        //验证成功将隐藏的控件显示出来，进行下一步修改
        btn_newPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPass = et_oldPass.getText().toString();
                if(oldPass.isEmpty()){
                    Toast.makeText(AlterPassActivity.this,"请输入原来的密码",Toast.LENGTH_SHORT).show();
                }else{
                    HttpUtils http = new HttpUtils();
                    String url= GlobalContacts.VISON_URL+"/userServlet?method=loginUser&phoneNumber="
                            +phoneNumber;
                    http.send(HttpRequest.HttpMethod.GET,
                            url,
                            new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String result = responseInfo.result;
                                    Gson gson = new Gson();
                                    User user = gson.fromJson(result, User.class);
                                    String user_password = user.getUser_password();
                                  //  Log.e("服务器获取的jsonString", user_mobile + "::" + user_password + "::" + user_id);
                                    if (user_password.equals(oldPass)){
                                        Toast.makeText(AlterPassActivity.this,"老密码正确！",Toast.LENGTH_SHORT).show();
                                        //显示隐藏的控件
                                        et_newPass1.setVisibility(View.VISIBLE);
                                        et_newPass2.setVisibility(View.VISIBLE);
                                        btn_alterPass.setVisibility(View.VISIBLE);
                                        ll_oldPass.setVisibility(View.INVISIBLE);
                                    }else {
                                        Toast.makeText(AlterPassActivity.this,"老密码错误！",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(HttpException e, String s) {

                                }
                            }
                    );
                }
            }
        });

        btn_alterPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String newpass1 = et_newPass1.getText().toString();
                 String newpass2 = et_newPass2.getText().toString();
                Log.e("pass",newpass1+newpass2+oldPass);
                 int user_id = LoginUtils.getUser_id(AlterPassActivity.this);
                 if(newpass1.equals(newpass2)){
                    if (newpass1.equals(oldPass)){
                        Toast.makeText(AlterPassActivity.this,"请设置新的密码！",Toast.LENGTH_SHORT).show();
                    }else{
                        //修改密码后台
                        HttpUtils httpUtils = new HttpUtils();
                        String url = GlobalContacts.VISON_URL+"/userServlet?method=alterPassword&user_id="+user_id+"&newPass="+newpass1;
                        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<Object>() {
                            @Override
                            public void onSuccess(ResponseInfo<Object> responseInfo) {
                                Toast.makeText(AlterPassActivity.this,"密码修改成功！",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(AlterPassActivity.this,"密码修改失败！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                 }else{
                     Toast.makeText(AlterPassActivity.this,"两次新密码密码要一致",Toast.LENGTH_SHORT).show();
                 }
            }
        });
    }

}
