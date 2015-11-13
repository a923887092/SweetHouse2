package com.gwm.sweethouse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.ZList.ZListView;
import com.gwm.sweethouse.adapter.BillAdapter;
import com.gwm.sweethouse.bean.Bill;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LoginUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.message.PushAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WalletRecorderActivity extends Activity {
    ZListView lv_bills;
    ProgressBar pb_loadBills;
    TextView tv_noBills;
    List<Bill> list;
    BillAdapter adapter;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_recorder);
        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(WalletRecorderActivity.this).onAppStart();
        initViews();
        list = new ArrayList<Bill>();
        adapter = new BillAdapter(WalletRecorderActivity.this,list);
        lv_bills.setAdapter(adapter);
        lv_bills.setXListViewListener(new ZListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                //下拉刷新，重新发送请求获取数据
                list.clear();
                getData();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // adapter.notifyDataSetChanged();
                        lv_bills.stopRefresh();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {


                    @Override
                    public void run () {
                        lv_bills.stopLoadMore();
                    }
                }, 1000);
            }
        });
        lv_bills.setPullLoadEnable(true);
        getData();
    }

    private void initViews() {
        lv_bills = (ZListView) findViewById(R.id.lv_bills);
       /* pb_loadBills = (ProgressBar) findViewById(R.id.pb_loadBills);
        tv_noBills = (TextView) findViewById(R.id.tv_noBills);*/

    }

    private void getData() {
        HttpUtils httpUtils = new HttpUtils();
        int user_id = LoginUtils.getUser_id(WalletRecorderActivity.this);
        String url = GlobalContacts.VISON_URL+"/BillServlet?method=getMyBill&user_id="+user_id;
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                Type typeOfT = new TypeToken<List<Bill>>(){}.getType();
                List<Bill> resultList= gson.fromJson(result, typeOfT);
                list.addAll(resultList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(WalletRecorderActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
