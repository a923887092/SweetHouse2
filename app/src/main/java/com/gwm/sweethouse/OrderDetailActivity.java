package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gwm.sweethouse.bean.Address;
import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class OrderDetailActivity extends Activity {
    TextView tv_detailOrderId, tv_detailUserName, tv_detailMoboil,
            tv_orderAddress, tv_detailProductName, tv_productDesc,
            tv_productPrice, tv_buyCount, tv_payType, tv_payNumb;
    ImageView iv_productImage;
    Button btn_detail1, btn_detail2, btn_detail3, btn_detail4;
    int order_state, buy_count, order_id, pay_id,add_id;
    String Product_name, product_photo, product_desc;
    float order_price;
    BitmapUtils bitmapUtils;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        getIntentData();
        iniiviews();
        getOrderAddress(add_id);
    }



    private void getIntentData() {
        Intent intent = getIntent();
        order_state = intent.getIntExtra("Order_state", 0);
        buy_count = intent.getIntExtra("buy_count", 0);
        order_id = intent.getIntExtra("order_id", 0);
        pay_id = intent.getIntExtra("pay_id", 0);
        Product_name = intent.getStringExtra("Product_name");
        product_photo = intent.getStringExtra("product_photo");
        product_desc = intent.getStringExtra("product_desc");
        order_price = intent.getFloatExtra("Order_price", 0);
        add_id = intent.getIntExtra("add_id",0);
        Log.e("intent", order_state + "," + buy_count + "," + order_id + "," + pay_id +
                Product_name + product_photo + product_desc + order_price+":::"+add_id);

    }

    private void iniiviews() {
        tv_detailOrderId = (TextView) findViewById(R.id.tv_detailOrderId);
        tv_detailUserName = (TextView) findViewById(R.id.tv_detailUserName);
        tv_detailMoboil = (TextView) findViewById(R.id.tv_detailMoboil);
        tv_orderAddress = (TextView) findViewById(R.id.tv_orderAddress);
        tv_detailProductName = (TextView) findViewById(R.id.tv_detailProductName);
        tv_productDesc = (TextView) findViewById(R.id.tv_productDesc);
        tv_productPrice = (TextView) findViewById(R.id.tv_productPrice);
        tv_buyCount = (TextView) findViewById(R.id.tv_buyCount);
        tv_payType = (TextView) findViewById(R.id.tv_payType);
        tv_payNumb = (TextView) findViewById(R.id.tv_payNumb);
        iv_productImage = (ImageView) findViewById(R.id.iv_detailProductImage);
        btn_detail1 = (Button) findViewById(R.id.btn_detail1);
        btn_detail2 = (Button) findViewById(R.id.btn_detail2);
        btn_detail3 = (Button) findViewById(R.id.btn_detail3);
        btn_detail4 = (Button) findViewById(R.id.btn_detail4);

        tv_detailOrderId.setText("SH"+order_id);
        tv_detailProductName.setText(Product_name);
        tv_productDesc.setText(product_desc);
        tv_productPrice.setText(order_price + "");
        tv_buyCount.setText("数量:"+buy_count+"件");
        tv_payNumb.setText("￥" + order_price);
        if (pay_id == 1){
            tv_payType.setText("在线支付");
        }else{
            tv_payType.setText("货到付款");
        }
        Log.e("image", GlobalContacts.VISON_URL + product_photo);
        bitmapUtils = new BitmapUtils(OrderDetailActivity.this);
        bitmapUtils.display(iv_productImage, GlobalContacts.VISON_URL + product_photo);
    }


    private void getOrderAddress(int add_id) {
        HttpUtils httpUtils = new HttpUtils();
        String url = GlobalContacts.VISON_URL+"/AddressServlet";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method","getAddressByAdd_id");
        params.addQueryStringParameter("add_id",add_id+"");
        httpUtils.send(
                HttpRequest.HttpMethod.GET, url, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                       String JsonString = responseInfo.result;
                        Address address = new Address();
                        Gson gson = new Gson();
                        address = gson.fromJson(JsonString,Address.class);
                        tv_detailUserName.setText(address.getAdd_name());
                        tv_detailMoboil.setText(address.getAdd_phone());
                        tv_orderAddress.setText(address.getAdd_address());
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                }
        );
    }
}
