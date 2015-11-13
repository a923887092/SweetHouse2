package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LoginUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.message.PushAgent;

public class PayOrderActivity extends Activity {
    TextView tv_payUser_id,tv_payStyle,tv_payMoney;
    Intent intent;
    int order_id,product_id,buy_count;
    float order_price;
    String product_name;
    Button btn_payIt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        getData();
        initViews();

        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(PayOrderActivity.this).onAppStart();
    }


    private void initViews() {
        tv_payUser_id = (TextView) findViewById(R.id.tv_payUser_id);
        tv_payStyle = (TextView) findViewById(R.id.tv_payStyle);
        tv_payMoney = (TextView) findViewById(R.id.tv_payMoney);

        tv_payUser_id.setText(LoginUtils.getUser_id(PayOrderActivity.this)+"");
        tv_payMoney.setText("￥"+order_price);
        btn_payIt = (Button) findViewById(R.id.btn_payIt);
        btn_payIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payOrder();
                Toast.makeText(PayOrderActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void getData() {
        intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);
        product_id = intent.getIntExtra("product_id", 0);
        order_price = intent.getFloatExtra("order_price", 0);
        buy_count = intent.getIntExtra("buy_count", 0);
        product_name = intent.getStringExtra("product_name");
        Log.e("payorderIntent1",order_id+"::"+product_id+"::"+order_price+"::"+buy_count+"::"+product_name);
    }

    private void payOrder() {
        Log.e("payorderIntent2",order_id+"::"+product_id+"::"+order_price+"::"+buy_count);
        int user_id = LoginUtils.getUser_id(PayOrderActivity.this);
        String url1 = GlobalContacts.VISON_URL+"/MyWalletServlet?method=payOrder&user_id="+user_id+"&order_price="+order_price;
        String url2 = GlobalContacts.VISON_URL+"/OrderServlet?method=payOrder&order_id="+order_id;
        Log.e("payorderIntent3",order_id+"::"+product_id+"::"+order_price+"::"+buy_count);
        String url3 = GlobalContacts.VISON_URL+"/ProductServlet?method=payOrder&product_id="+product_id+"&buy_count="+buy_count;
        int bill_state = 2;
        String url4 = GlobalContacts.VISON_URL+"/BillServlet?method=addBill&product_name="+product_name+"&order_price="+order_price+"&bill_state="+ bill_state +"&user_id="+user_id;
        HttpUtils httpUtils = new HttpUtils();
        //将钱包中的余额减去相应数目
        httpUtils.send(HttpRequest.HttpMethod.GET, url1, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {

            }
            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
        //将订单状态改为已支付
        httpUtils.send(HttpRequest.HttpMethod.GET, url2, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

        //将相应的商品库存减去相应数目，售出加上相应数目
        httpUtils.send(HttpRequest.HttpMethod.GET, url3, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

        //新增一条账单记录
       // http://localhost:8080/SweetHouse/MyWalletServlet?method=addBill&user_id=123458
        // &product_name=product1&order_price=1009&bill_state=2
        httpUtils.send(HttpRequest.HttpMethod.GET, url4, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
