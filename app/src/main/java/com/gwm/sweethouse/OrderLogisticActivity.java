package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.ZList.ZListView;
import com.gwm.sweethouse.bean.Logistic;
import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.message.PushAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class OrderLogisticActivity extends Activity {
    MyLogisticAdapter adapter;
    List<Logistic> list;
    ZListView lv_logistic;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_logistic);
        lv_logistic =(ZListView)findViewById(R.id.lv_logistic);
        list = new ArrayList<>();
        adapter = new MyLogisticAdapter(OrderLogisticActivity.this,list);
        lv_logistic.setAdapter(adapter);

        lv_logistic.setXListViewListener(new ZListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                //下拉刷新，重新发送请求获取数据
                list.clear();
                getData();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // adapter.notifyDataSetChanged();
                        lv_logistic.stopRefresh();
                    }
                }, 1000);

            }

            @Override
            public void onLoadMore() {

                handler.postDelayed(new Runnable() {


                        @Override
                        public void run () {
                            lv_logistic.stopLoadMore();
                        }
                }, 1000);

            }
        });
        lv_logistic.setPullLoadEnable(true);
      /*  lv_logistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(OrderLogisticActivity.this, "onItemClick=" + position,
                        Toast.LENGTH_SHORT).show();

            }

        });
*/
       /* lv_logistic.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(OrderLogisticActivity.this,
                        "onItemLongClick=" + position, Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
        });*/
        getData();
        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(OrderLogisticActivity.this).onAppStart();
    }

    private void getData() {
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.configCurrentHttpCacheExpiry(5*1000);
        // 设置超时时间
        httpUtils.configTimeout(5*1000);
        httpUtils.configSoTimeout(5*1000);
        // 设置缓存5秒,5秒内直接返回上次成功请求的结果。
        httpUtils.configCurrentHttpCacheExpiry(5*1000);

        String url = GlobalContacts.VISON_URL + "/LogisticServlet";
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e("result",result);
                Gson gson = new Gson();
                Type typeOfT = new TypeToken<List<Logistic>>(){}.getType();
                List<Logistic> resultList= gson.fromJson(result, typeOfT);
                list.addAll(resultList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(OrderLogisticActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyLogisticAdapter extends BaseAdapter{
        Context context;
        List<Logistic> list = new ArrayList<Logistic>();
        LayoutInflater inflater;
        LogViewHolder holder;

        public MyLogisticAdapter(Context context, List list){
            this.context = context;
            this.list = list;
            this.inflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view==null){
                view = inflater.inflate(R.layout.item_list_logistic,null);
                holder = new LogViewHolder();
                holder.tv_logContext = (TextView) view.findViewById(R.id.tv_logContext);
                holder.tv_logTime = (TextView) view.findViewById(R.id.tv_logTime);
                view.setTag(holder);
            }else{
                holder = (LogViewHolder) view.getTag();
            }
            holder.tv_logContext.setText(list.get(i).getLogContext());
            holder.tv_logTime.setText(list.get(i).getLogTime());
            return view;
        }

        private class LogViewHolder{
            TextView tv_logContext,tv_logTime;
        }
    }

}

