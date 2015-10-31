package com.gwm.sweethouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2015/10/20.
 */
//申请量房
public class GetRoomActivity extends Activity implements View.OnClickListener {
    EditText phonEditText, verEditText,orderName;
    private Button btnYuyue;
    private Spinner spinner;
    private List<String> addrList;
    private  ArrayAdapter<String> arrAdapter;
    private View view;
    private Button btnGetyzm;
    private String APPKEY="b636b56105b4";
    private String APPSECRET="df2fcc94f04b674cec0985266f0945fe";
    private Handler handler;
    private String phString;
    private Button btnMu;
    int id;
    String tel,city,order_name,phoneNum;
    private String TAG="点击了对话框";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_room);
        initView();
        spinner= (Spinner) findViewById(R.id.sp_adrr);
        btnGetyzm= (Button) btnGetyzm.findViewById(R.id.btn_getyzm);
        addrList=new ArrayList<String>();
        addrList.add("请选择您的城市");
        addrList.add("苏州");
        addrList.add("上海");
        addrList.add("南京");
        arrAdapter=new ArrayAdapter<String>(this,R.layout.address_textsize,addrList);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);
        phoneNum=btnMu.getText().toString();
        btnMu.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                AlertDialog dialog=
                new AlertDialog.Builder(GetRoomActivity.this)
                        .setIcon(null)
                        .setTitle("联系客服?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                                startActivity(intent);
                                //activity finish
                                finish();
                            }

                        }).setNegativeButton("取消",null)
                        .show();

            }
        });

        //启动SDK
        SMSSDK.initSDK(GetRoomActivity.this, APPKEY, APPSECRET);
        SMSSDK.registerEventHandler(eh); //注册短信回调

        handler=new Handler(){

            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        Toast.makeText(GetRoomActivity.this,"验证成功",Toast.LENGTH_LONG).show();
                      break;
                    default:
                        break;
                }
            }

        };
    }
    private void initView(){
        spinner= (Spinner) findViewById(R.id.sp_adrr);
        btnGetyzm= (Button) findViewById(R.id.btn_getyzm);
        phonEditText= (EditText) findViewById(R.id.ed_shouji);
        verEditText= (EditText) findViewById(R.id.ed_yanzheng);
        btnYuyue= (Button) findViewById(R.id.btn_yuyue);
        orderName= (EditText) findViewById(R.id.ed_chenghu);
        btnGetyzm.setOnClickListener(this);
        btnYuyue.setOnClickListener(this);
        btnMu= (Button) findViewById(R.id.btn_mumu);
    }



    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.btn_getyzm://获取验证码
                tel=phonEditText.getText().toString();

                if(!TextUtils.isEmpty(tel)){
                    //getVerificationCode用于向服务器请求发送验证码的服务，需要传递国家代号和接收验证码的手机号码，
                    //请求getVerificationCode的时间间隔不应该小于60秒，否则服务端会返回“操作过于频繁”的错误
                    SMSSDK.getVerificationCode("86",tel);
                    Toast.makeText(this, "验证码已发送:" + tel, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_yuyue://校验验证码
                city=spinner.toString();
                order_name=orderName.getText().toString();
                if(!TextUtils.isEmpty(verEditText.getText().toString())) {
                   addUser(id, tel, city, order_name);
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
                //((Throwable)data).printStackTrace();
                Log.e(";;;;;;;;;;;",":::::::::");
            }
        }
    };

    //将用户名和密码添加到数据库中
    public void addUser(int id, String tel,String city,String order_name) {
        Log.e("信息验证",id+tel+city+order_name);
        HttpUtils http = new HttpUtils();
        String url= GlobalContacts.SERVER_URL+"/ZZXOrderServlet?method=addUser&id="
                +id+"&tel="+tel+"&city="+city.toString()+"&order_name="+order_name;
        Log.e("btt",url+"");
        http.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Toast.makeText(GetRoomActivity.this, "预约成功！", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(GetRoomActivity.this, "预约失败，请重新预约", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }




}
