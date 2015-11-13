package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LoginUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class EvaluateOrderActivity extends Activity {
    ImageButton ibtn_return;
    RatingBar ratingbar1,ratingbar2,ratingbar3;
    ImageView iv_evaluateImage;
    EditText et_commentContent;
    Button btn_evaluate;
    Intent intent;
    int user_id;
    int order_id;
    int product_id;
    String uri;
    float currentRating1,currentRating2,currentRating3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        initViews();
        getData();
    }

    private void getData() {
        intent = getIntent();
        order_id =  intent.getIntExtra("order_id", 0);
        product_id = intent.getIntExtra("product_id",0);
        uri =  intent.getStringExtra("uri");
        user_id = LoginUtils.getUser_id(EvaluateOrderActivity.this);
        Log.e("evaluateIntent", order_id + "::" + uri + "::" + user_id);
        BitmapUtils bitmapUtils = new BitmapUtils(EvaluateOrderActivity.this);
        bitmapUtils.display(iv_evaluateImage, GlobalContacts.VISON_URL + uri);
    }


    private void initViews() {
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_evaluateToOrder);
        iv_evaluateImage = (ImageView) findViewById(R.id.iv_evaluateImage);
        et_commentContent = (EditText) findViewById(R.id.et_commentContent);
        btn_evaluate = (Button) findViewById(R.id.btn_evaluate);
        ratingbar1 = (RatingBar) findViewById(R.id.ratingbar1);
        ratingbar2 = (RatingBar) findViewById(R.id.ratingbar2);
        ratingbar3 = (RatingBar) findViewById(R.id.ratingbar3);

        ratingbar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(EvaluateOrderActivity.this,v+"颗星",Toast.LENGTH_SHORT).show();
            }
        });
        ratingbar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(EvaluateOrderActivity.this,v+"颗星",Toast.LENGTH_SHORT).show();
            }
        });
        ratingbar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(EvaluateOrderActivity.this,v+"颗星",Toast.LENGTH_SHORT).show();
            }
        });

        ibtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToWeb();
            }
        });
    }

    private void sendToWeb() {
        currentRating1 = ratingbar1.getRating();
        currentRating2 = ratingbar2.getRating();
        currentRating3 = ratingbar3.getRating();
        float aveCurrentRating = (currentRating1+currentRating2+currentRating3)/3;
        String comment_content = et_commentContent.getText().toString();
        Log.e("star",currentRating1+"::"+currentRating2+"::"+currentRating3);
        String url1 = GlobalContacts.VISON_URL+"/ProductCommentServlet";
        String url2 = GlobalContacts.VISON_URL+"/OrderServlet?method=evaluateOrder&order_id="+order_id;
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method","addComment");
        params.addQueryStringParameter("user_id",user_id+"");
        params.addQueryStringParameter("order_id",order_id+"");
        params.addQueryStringParameter("product_id",product_id+"");
        params.addQueryStringParameter("comment_grade",aveCurrentRating+"");
        params.addQueryStringParameter("comment_content",comment_content);
        HttpUtils httpUtils  = new HttpUtils();
        //将评论内容添加到数据库
        httpUtils.send(HttpRequest.HttpMethod.GET, url1, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Toast.makeText(EvaluateOrderActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(EvaluateOrderActivity.this,"评论失败，请重试！",Toast.LENGTH_SHORT).show();
            }
        });
        //将订单状态改变为已完成
        httpUtils.send(HttpRequest.HttpMethod.GET, url2, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


}
