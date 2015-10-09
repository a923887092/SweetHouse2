package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity {
    private Button loginButton,registerButton;
    private ImageButton returnButton;
    private EditText userEditText,passEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.btn_login);
        registerButton = (Button) findViewById(R.id.btn_register);
        returnButton = (ImageButton) findViewById(R.id.btn_return);
        userEditText = (EditText) findViewById(R.id.et_username);
        passEditText = (EditText) findViewById(R.id.et_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                if(validate()) {
                    //Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    // 输入框空值验证方法
    private boolean validate(){
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();
        if(username.equals("")){
            Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.equals("")){
            Toast.makeText(LoginActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    // 登录方法,获取输入框中的用户名与数据库中密码数据对比,正确返回true
    private boolean login(){
      return true;

    }

    // 将用户名和密码保存到配置文件，
    private void saveUserMsg(String msg){
        // 用户编号
        String id = "";
        // 用户名称
        String name = "";
        // 获得信息数组
        String[] msgs = msg.split(";");
        int idx = msgs[0].indexOf("=");
        id = msgs[0].substring(idx+1);
        idx = msgs[1].indexOf("=");
        name = msgs[1].substring(idx+1);
        // 共享信息
        SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("id", id);
        editor.putString("name", name);
        editor.commit();
    }
}
