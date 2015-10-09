package com.gwm.sweethouse;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity2 extends Activity {

    EditText editText1,editText2;
    String firstPass,secondPass;
    Intent intent;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        editText1 = (EditText) findViewById(R.id.et_remind1);
        editText2 = (EditText) findViewById(R.id.et_remind2);
        intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
    }

    public void toRegister(View view){
        Intent intent = new Intent(RegisterActivity2.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void commitRegister(View view){
        if (firstPass==""){

            Toast.makeText(RegisterActivity2.this,"初始密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (secondPass == ""){
            Toast.makeText(RegisterActivity2.this,"请再次输入初始密码",Toast.LENGTH_SHORT).show();
        }else{
            //将用户名和密码添加到数据库
            addToDatabase();

            //然后跳转到首页
            Intent intent = new Intent(RegisterActivity2.this, MainActivity.class);
            startActivity(intent);
        }
    }

    //将用户名和密码添加到数据库中
    public void addToDatabase(){
        firstPass = editText1.getText().toString();
        secondPass = editText2.getText().toString();
        Log.e(".....",phoneNumber+"...."+firstPass);

        //将用户名和密码添加到数据库中
    }
}
