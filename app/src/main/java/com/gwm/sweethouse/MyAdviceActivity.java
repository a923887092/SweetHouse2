package com.gwm.sweethouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class MyAdviceActivity extends Activity implements View.OnClickListener {
    ImageButton ibtn_return;
    RelativeLayout rl_adviceKind;
    EditText et_adviceText,et_contact_type;
    TextView tv_textNum,tv_adviceKind;
    Handler handler ;
    Button btn_giveAdvice;
    String advice_type,advice_content,contact_type;
    String[] adviceTypes = {"功能意见","页面意见","操作意见","流量问题","新的需求","其他意见"};
    private int user_id;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        initViews();
        intent = getIntent();
        user_id = intent.getIntExtra("user_id",0);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        tv_adviceKind.setText(msg.obj.toString());
                        advice_type = tv_adviceKind.getText().toString();
                        break;
                    case 1:
                        tv_adviceKind.setText(msg.obj.toString());
                        advice_type = tv_adviceKind.getText().toString();
                        break;
                    case 2:
                        tv_adviceKind.setText(msg.obj.toString());
                        advice_type = tv_adviceKind.getText().toString();
                        break;
                    case 3:
                        tv_adviceKind.setText(msg.obj.toString());
                        advice_type = tv_adviceKind.getText().toString();
                        break;
                    case 4:
                        tv_adviceKind.setText(msg.obj.toString());
                        advice_type = tv_adviceKind.getText().toString();
                        break;
                    case 5:
                        tv_adviceKind.setText(msg.obj.toString());
                        advice_type = tv_adviceKind.getText().toString();
                        break;
                }
            }
        };
    }


    private void initViews() {
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_adviceToMy);
        rl_adviceKind = (RelativeLayout) findViewById(R.id.rl_adviceKind);
        et_adviceText = (EditText) findViewById(R.id.et_adviceText);
        tv_textNum = (TextView) findViewById(R.id.tv_textNum);
        tv_adviceKind = (TextView) findViewById(R.id.tv_adviceKind);
        btn_giveAdvice = (Button) findViewById(R.id.btn_giveAdvice);
        et_contact_type = (EditText) findViewById(R.id.et_contact_type);

        ibtn_return.setOnClickListener(this);
        rl_adviceKind.setOnClickListener(this);
        btn_giveAdvice.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibtn_adviceToMy:
                finish();
                break;
            case R.id.rl_adviceKind:
                showKindDialog();
                break;
            case R.id.btn_giveAdvice:
                commitAdvice();
                finish();
                break;

        }
    }

    private void commitAdvice() {
        advice_type = tv_adviceKind.getText().toString();
        advice_content = et_adviceText.getText().toString();
        contact_type = et_contact_type.getText().toString();
        Log.e("jjjjjj", advice_type + advice_content + contact_type);
        if (advice_content.isEmpty()){
            Toast.makeText(MyAdviceActivity.this,"请填写反馈内容",Toast.LENGTH_SHORT).show();
        }else{
            HttpUtils httpUtils = new HttpUtils();
            String url = GlobalContacts.VISON_URL+"/AdviceServlet";
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("method","addAdvice");
            params.addQueryStringParameter("user_id",user_id+"");
            params.addQueryStringParameter("advice_type",advice_type);
            params.addQueryStringParameter("advice_content",advice_content);
            params.addQueryStringParameter("contact_type", contact_type);
            params.setContentType("text/json,charset=utf-8");
            Log.e("android编码",params.getCharset());
            httpUtils.send(HttpRequest.HttpMethod.GET, url, params,
                    new RequestCallBack<Object>() {
                        @Override
                        public void onSuccess(ResponseInfo<Object> responseInfo) {

                            Toast.makeText(MyAdviceActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(MyAdviceActivity.this,"提交失败，请重试",Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void showKindDialog() {
        final Message msg = new Message();
        new AlertDialog.Builder(this)
                .setItems(adviceTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                msg.what = 0;
                                msg.obj = "功能意见";
                                handler.sendMessage(msg);
                                break;
                            case 1:
                                msg.what = 1;
                                msg.obj = "页面意见";
                                handler.sendMessage(msg);
                                break;
                            case 2:
                                msg.what = 2;
                                msg.obj = "操作意见";
                                handler.sendMessage(msg);
                                break;
                            case 3:
                                msg.what = 3;
                                msg.obj = "流量问题";
                                handler.sendMessage(msg);
                                break;
                            case 4:
                                msg.what = 4;
                                msg.obj = "新的需求";
                                handler.sendMessage(msg);
                                break;
                            case 5:
                                msg.what = 5;
                                msg.obj = "其他意见";
                                handler.sendMessage(msg);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
