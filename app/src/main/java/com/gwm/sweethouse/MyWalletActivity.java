package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gwm.sweethouse.bean.Wallet;
import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class MyWalletActivity extends Activity implements View.OnClickListener{
    ImageButton ibtn_return;
    RelativeLayout rl_walletBg,rl_aboutWallet;
    TextView tv_money;
    Wallet wallet = new Wallet();
    Handler handler;
    private int user_id;
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        initViews();
        intent = getIntent();
        user_id = intent.getIntExtra("user_id",0);
        getMoney();
        /*tv_money.setText(wallet.getWallet_balance() + "");
        Log.e("wallet",wallet.toString());*/
        handler =  new Handler(){
            @Override
            public void handleMessage(Message msg) {
               switch (msg.what){
                   case 0:
                       wallet = (Wallet)msg.obj;
                       tv_money.setText(wallet.getWallet_balance() + "");
                       break;
               }
            }
        };
    }

    private void initViews() {
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_walletToMy);
        rl_walletBg = (RelativeLayout) findViewById(R.id.rl_walletBg);
        rl_walletBg.getBackground().setAlpha(75);
        rl_aboutWallet = (RelativeLayout) findViewById(R.id.rl_aboutWallet);
        tv_money = (TextView) findViewById(R.id.tv_money);

        rl_walletBg.setOnClickListener(this);
        rl_aboutWallet.setOnClickListener(this);
        ibtn_return.setOnClickListener(this);
    }

    //从服务器获取钱包余额信息
    private void getMoney() {
        HttpUtils httpUtils = new HttpUtils();
        String url = GlobalContacts.VISON_URL+"/MyWalletServlet?method=getBalanceByUserId&user_id="+user_id;
        httpUtils.send(HttpRequest.HttpMethod.GET,
                url, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                            String jsonString = responseInfo.result;
                            Gson gson = new Gson();
                            wallet = gson.fromJson(jsonString,Wallet.class);
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = wallet;
                            handler.sendMessage(msg);
                            Log.e("wallet success",wallet.toString());
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.e("wallet success",".......");
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibtn_walletToMy:
                finish();
                break;
            case R.id.rl_walletBg:
                startActivity(new Intent(MyWalletActivity.this,WalletRecorderActivity.class));
                break;
            case R.id.rl_aboutWallet:
                startActivity(new Intent(MyWalletActivity.this,AboutWalletActivity.class));
                break;
        }
    }
}
