package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gwm.sweethouse.adapter.OrderListAdapter;
import com.gwm.sweethouse.bean.OrderListBean;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends Activity {
    private ListView listView;
    private OrderListBean orderListBean;
    List<OrderListBean> list=new ArrayList<OrderListBean>();
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_order);
        Log.e("111111111111", "oncreat");
        listView = (ListView) findViewById(R.id.listorders);
        getData();
        adapter=new OrderListAdapter(MyOrderActivity.this,list);
        listView.setAdapter((ListAdapter) adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("2222222222222222","onItem");
                String text = list.get(position).getGoodsname();
                Toast.makeText(MyOrderActivity.this, text, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData() {

        for (int i=0;i<6;i++) {
            Log.e("33333333333","chongfu");
            OrderListBean orderListBean1=new OrderListBean();
            orderListBean1 = new OrderListBean();
            orderListBean1.setGoodsDescribe("这是商品1");
            orderListBean1.setGoodsname("商品1");
            orderListBean1.setPrice(120);
            orderListBean1.setImagesrc(R.drawable.alipay_icon);
            list.add(orderListBean1);

        }
    }
}
