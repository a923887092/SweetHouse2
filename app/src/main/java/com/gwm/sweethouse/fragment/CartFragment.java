package com.gwm.sweethouse.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.OrderConfirmationActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.CartAdapter;
import com.gwm.sweethouse.bean.CartBean;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.CacheUtils;
import com.gwm.sweethouse.view.RefreshListview;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/6.
 */
public class CartFragment extends Fragment{
    static List<CartBean> list=new ArrayList<CartBean>();
    static CartAdapter adapter;
    RefreshListview cartlistview;
    View cartview;
    static HttpUtils httpUtils;
    private  String url;
    static Button balance_btn;
    public static int checkNum=0;
    private static CheckBox allcheckBox;
    TextView carttextview;
    static TextView summationTextView;
    Gson gson=new Gson();
    LayoutInflater inflater;
    View footerview;
    static Context context;
    public static Handler handler;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        checkNum++;
                        summa();
                        balance_btn.setText("结算("+checkNum+")");
                        if (checkNum==list.size()){
                            allcheckBox.setChecked(true);
                        }else{
                            allcheckBox.setChecked(false);
                        }

                        break;
                    case 2:
                        checkNum--;
                        summa();
                        balance_btn.setText("结算(" + checkNum + ")");
                        if (checkNum==list.size()){
                            allcheckBox.setChecked(true);
                        }else{
                            allcheckBox.setChecked(false);
                        }
                        break;
                    case 3:
                        Bundle bundle=new Bundle();
                        bundle=msg.getData();
                        Boolean checked=bundle.getBoolean("checked");
                        if (checked){
                            summa();
                        }

                        int change=bundle.getInt("change");
                        int cartid= bundle.getInt("cart_id");
                        Log.e("MESSEGE",cartid+""+change+checked);
                        sendClickToServer(change, cartid);
                        break;

                }
                super.handleMessage(msg);
            }
        };
    }



    private static void sendClickToServer(Object change,int cart_id) {

            httpUtils=new HttpUtils();

            Date date=new Date();
            String time= String.valueOf(date.getTime());
            Log.e("time",time);
        String url=null;
        //http://tl:8080/sweethouse/ChangeCart?user_id=1&cart_id=6&change=0
        if (TextUtils.isEmpty(String.valueOf(change))){
            url=GlobalContacts.CHANGE_URL+"?user_id=1" + "&cart_id=" + cart_id +"&time=" + time;
        }else {
           url = GlobalContacts
                    .CHANGE_URL + "?user_id=1" + "&cart_id=" + cart_id + "&change=" + change + "&time=" + time;
        }
            httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Log.e("success", "成功了吗");
                }


                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(context, "网络环境较差", Toast.LENGTH_SHORT).show();
                }
            });
        }


    //根据list中存储的每个item中checkbox的状态来计算总的金额
    private static void summa() {
        int sumation=0;
        for (int i=0;i<list.size();i++){
            if (adapter.getselectedList().get(i)){
                sumation+=list.get(i).getGoods_amount()*list.get(i).getPrice();
            }
        }
        summationTextView.setText(sumation + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cartview= inflater.inflate(R.layout.pager_cart,container,false);
        this.context = inflater.getContext();
        initViews();
        initData();

        return cartview;
    }

    //初始化视图
    private void initViews() {

        cartlistview= (RefreshListview) cartview.findViewById(R.id.item_cart);
        balance_btn= (Button) cartview.findViewById(R.id.balance);
        //listView.setAdapter((ListAdapter) adapter);
        allcheckBox= (CheckBox) cartview.findViewById(R.id.allchecked);
        allcheckBox.setChecked(false);
        carttextview= (TextView) cartview.findViewById(R.id.cart);


        //全选按钮的点击事件
        allcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() != 0) {
                    if (allcheckBox.isChecked() == true) {
                        for (int i = 0; i < list.size(); i++) {
                            adapter.getselectedList().set(i, true);
                        }
                        summa();
                        checkNum = list.size();
                        dataChanged();
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            adapter.getselectedList().set(i, false);
                        }
                        summationTextView.setText(0+"");
                        checkNum = 0;
                        dataChanged();
                    }

                }
            }


        });

        summationTextView= (TextView) cartview.findViewById(R.id.summation);
        //结算按钮 讲checkbox中状态为true的部分传入一个新的list并讲其传给订单确认界面
        balance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNum==0){
                    Toast.makeText(getActivity(),"亲还没有选好商品哦",Toast.LENGTH_SHORT).show();
                }
                else{
                    ArrayList<CartBean> BundleList=new ArrayList<CartBean>();
                    //传输数组的临时索引
                    int tempindex=0;
                    for (int i=0;i<list.size();i++) {
                        if (adapter.getselectedList().get(i)){

                            BundleList.add(tempindex, list.get(i));
                            tempindex++;
                        }
                    }
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cart", (Serializable)BundleList);
                    intent.putExtras(bundle);
                    intent.setClass(getActivity(), OrderConfirmationActivity.class);
                    startActivity(intent);
                }
            }
        });

        //设置下拉刷新监听
        cartlistview.setOnRefreshListener(new RefreshListview.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                getDataFromWeb();
            }
        });

        //listview的滑动监听事件
        cartlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState==SCROLL_STATE_IDLE&&
                    cartlistview.getLastVisiblePosition()==adapter.getCount()){
                   // footerview.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


            }
        });


        //item长按删除的事件
        cartlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAlert(position-1);
                sendClickToServer(null,list.get(position-1).getCart_id());
                return false;
                
            }

            

            //定义弹窗的各种属性
            private void showAlert(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("确认要删除该商品吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "删除第" + position, Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                String result = gson.toJson(list);
                CacheUtils.setCache(GlobalContacts.CART_URL, result, getActivity());
                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }

    private void deleteCartToServer() {

    }

    private void dataChanged() {
        adapter.notifyDataSetChanged();
        balance_btn.setText("结算(" + checkNum + ")");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //初始化数据
    private void initData() {
        //先清空一遍list防止重复加载
        if (list.size()!=0){
            list.clear();
        }
        //从缓存中拿到json数据并解析数据，并且在后台再次从服务器中获取数据
        String Cache= CacheUtils.getCache(GlobalContacts.CART_URL, getActivity());
        if (!TextUtils.isEmpty(Cache)) {
            String result = Cache;
            fromGsonToList(result);
            //重新绑定adapter刷新界面
            if (list.size()!=0){
             /*   inflater=LayoutInflater.from(getActivity());
                footerview=inflater.inflate(R.layout.listview_footer, null);
                cartlistview.addFooterView(footerview);*/

                adapter = new CartAdapter(list, getActivity());
                cartlistview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


        }
        //在后台中从网络获取数据
        getDataFromWeb();


    }

    private void fromGsonToList(String result) {

            list = gson.fromJson(result, new TypeToken<List<CartBean>>() {
            }.getType());

    }

    private void getDataFromWeb( ){
        httpUtils=new HttpUtils();

        Date date=new Date();
        String time= String.valueOf(date.getTime());

        url=GlobalContacts.CART_URL+"?user_id=1"+"&time="+time;

        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                list.clear();


                String result = responseInfo.result;
                fromGsonToList(result);

                if (list.size() != 0) {

/*                    inflater=LayoutInflater.from(getActivity());
                    View footerview=inflater.inflate(R.layout.listview_footer,null);
                    cartlistview.addFooterView(footerview);*/
                    carttextview.setText("购物车(" + list.size() + ")");

                    adapter = new CartAdapter(list, getActivity());
                    cartlistview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                CacheUtils.setCache(GlobalContacts.CART_URL, result, getActivity());
                cartlistview.onRefreshComplete();
            }


            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(), "网络环境较差", Toast.LENGTH_LONG).show();
                cartlistview.onRefreshComplete();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        String result=gson.toJson(list);
        CacheUtils.setCache(GlobalContacts.CART_URL, result, getActivity());


    }

}
