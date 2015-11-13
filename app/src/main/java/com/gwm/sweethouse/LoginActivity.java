package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class LoginActivity extends Activity implements View.OnClickListener{
    private Button loginButton,registerButton;
    private ImageButton returnButton;
    private EditText userEditText,passEditText;
    private TextView forgetPass;
    String phoneNumber ;
    String password ;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(LoginActivity.this).onAppStart();
    }



    private void initViews() {
        loginButton = (Button) findViewById(R.id.btn_login);
        registerButton = (Button) findViewById(R.id.btn_register);
        returnButton = (ImageButton) findViewById(R.id.btn_return);
        userEditText = (EditText) findViewById(R.id.et_username);
        passEditText = (EditText) findViewById(R.id.et_password);
        forgetPass = (TextView) findViewById(R.id.tv_forgetPass);

        forgetPass.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
    }


    // 输入框空值验证方法
    private boolean validate(){
        phoneNumber = userEditText.getText().toString();
        password = passEditText.getText().toString();
        if(phoneNumber.equals("")){
            Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.equals("")){
            Toast.makeText(LoginActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    // 登录方法,获取输入框中的用户名与数据库中密码数据对比,正确返回true
    private void login( final String phoneNumber, final String password){
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
                        User user = gson.fromJson(result,User.class);
                        int user_id = user.getUser_id();
                        String user_mobile = user.getUser_mobile();
                        String user_password = user.getUser_password();
                        Log.e("服务器获取的jsonString", user_mobile + "::" + user_password + "::" + user_id);
                        if (user_password.equals(password)){
                            //将用户名密码持久化到SharedPreferences文件
                            saveUserMsg(phoneNumber,user_id);
                            Toast.makeText(LoginActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
                        }else {
                            //用户名正确，密码输入错误
                            Toast.makeText(LoginActivity.this,"密码错误！",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(LoginActivity.this, "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    // 将用户名和密码保存到配置文件，
    private void saveUserMsg( String phoneNumber,int user_id){
        //创建一个名为login的 preference
        preferences = this.getSharedPreferences("login", MODE_PRIVATE);
        //创建edit用来添加数据
        editor = preferences.edit();
        editor.putString("phoneNumber",phoneNumber);
        editor.putInt("user_id",user_id);
        editor.putBoolean("loginState",true);
        editor.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                validate();
                if(validate() == true) {
                    //Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Log.e("登陆数据", phoneNumber + "::" + password);
                    Toast.makeText(LoginActivity.this, phoneNumber + "::" + password, Toast.LENGTH_SHORT).show();
                    login(phoneNumber, password);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            break;
            case R.id.btn_register:
               /* SharedPreferences preferences = getSharedPreferences("login",LoginActivity.MODE_PRIVATE);
                int i = preferences.getInt("user_id", 0);
                String phoneNumber = preferences.getString("phoneNumber", "");
                String password = preferences.getString("password","");
                boolean loginState = preferences.getBoolean("loginState",false);
                Log.e("持久化的数据",i+"::"+phoneNumber+"::"+password+"::"+loginState);*/
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            break;
            case R.id.btn_return:
                finish();
            break;
            case R.id.tv_forgetPass:
                startActivity(new Intent(LoginActivity.this, ForgetPassACtivity.class));
                break;
        }
    }

}
