package com.gwm.sweethouse;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class OrderZxActivity extends Activity implements View.OnClickListener{
    private DatePickerDialog dialog;
    EditText phonEditText, verEditText,ordName;
    private Button btnYuyue;
    private Spinner spinner;
    private List<String> addrList;
    private ArrayAdapter<String> arrAdapter;
    private View view;
    private Button btnGetyzm;
    private String APPKEY="b636b56105b4";
    private String APPSECRET="df2fcc94f04b674cec0985266f0945fe";
    private Handler handler;
    private String phString;
    private ImageButton ibtnClose;
    private RelativeLayout ibtnBack;
    private TextView ordTime;
    private int year,monthOfYear,dayOfMonth;
    int id;
    String tel,orderName,addr,orderTime;
    private String TAG="点击了对话框";
    private int comOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_zx);
        Intent intent = getIntent();
        comOrderId = intent.getIntExtra("comOrderId", 0);

         //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(OrderZxActivity.this).onAppStart();

        initView();
        spinner= (Spinner) findViewById(R.id.sp_adrr);
        btnGetyzm= (Button) btnGetyzm.findViewById(R.id.btn_getyzm);
        addrList=new ArrayList<String>();
        addrList.add("苏州");
        addrList.add("上海");
        addrList.add("南京");
        addrList.add("无锡");
        addrList.add("常熟");
        arrAdapter=new ArrayAdapter<String>(this,R.layout.address_textsize,addrList);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        monthOfYear=calendar.get(Calendar.MONTH);
        dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        ordTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(OrderZxActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ordTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                },year,monthOfYear,dayOfMonth);
                datePickerDialog.show();
            }
        });
       /*alog=new DatePickerDialog(OrderZxActivity.this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
             String text=year+"-"+monthOfYear+"-"+dayOfMonth;
                ordTime.setText(text);
            }
        });*/
        //启动SDK
        SMSSDK.initSDK(OrderZxActivity.this, APPKEY, APPSECRET);
        SMSSDK.registerEventHandler(eh); //注册短信回调

        handler=new Handler(){

            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        Toast.makeText(OrderZxActivity.this, "验证成功", Toast.LENGTH_LONG).show();
                        addOrder(id, tel, addr, orderName, comOrderId, orderTime);
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
        ordName= (EditText) findViewById(R.id.ed_chenghu);
        ordTime= (TextView) findViewById(R.id.ed_ordTime);
        ibtnBack= (RelativeLayout) findViewById(R.id.rl_back);
        ibtnClose= (ImageButton) findViewById(R.id.ibtn_close);
        btnGetyzm.setOnClickListener(this);
        btnYuyue.setOnClickListener(this);
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
                addr=spinner.toString();
                orderName=ordName.getText().toString();
                orderTime=ordTime.getText().toString();
                if(!TextUtils.isEmpty(verEditText.getText().toString())) {
                    Log.e("btt",".................");
                  //  addOrder(id,tel,addr,orderName,comOrderId,orderTime);
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
    public void addOrder(int id, String tel,String addr,String orderName,int comId,String orderTime) {
        Log.e("btt",id+tel+addr+orderName+comId+orderTime);
        HttpUtils http = new HttpUtils();
        String url= GlobalContacts.SERVER_URL+"/DesOrderServlet?method=addOrder&id="
                +id+"&tel="+tel+"&addr="+addr.toString()+"&orderName="+orderName.toString()+"&comId="+comId+"&orderTime="+orderTime;

        Log.e("btt",url+"");
        http.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Toast.makeText(OrderZxActivity.this, "预约成功！", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(OrderZxActivity.this, "预约失败，请重新预约", Toast.LENGTH_SHORT).show();
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
