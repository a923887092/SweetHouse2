package com.gwm.sweethouse.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gwm.sweethouse.LoginActivity;
import com.gwm.sweethouse.MyAddressActivity;
import com.gwm.sweethouse.MyAdviceActivity;
import com.gwm.sweethouse.MyOrderActivity;
import com.gwm.sweethouse.MyWalletActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.ReturnSalesActivity;
import com.gwm.sweethouse.UserInfoActivity;
import com.gwm.sweethouse.adapter.MyOptionAdapter;
import com.gwm.sweethouse.bean.MyOptionData;
import com.gwm.sweethouse.bean.Wallet;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LoginUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/5.
 */
public class MyFragment extends Fragment implements AdapterView.OnItemClickListener,OnClickListener {

    List<MyOptionData> list = new ArrayList<MyOptionData>();
    private ImageView userPhoto;
    private TextView textView1;
    private TextView textView2;
    LinearLayout layout1,layout2,layout3,layout4,layout5;
    ImageButton ibtn_setting,ibtn_message;
    private ListView listview;
    SharedPreferences preferences;
    int user_id;
    String phoneNumber;
    boolean loginState;
    float balance;
    private Wallet wallet;
    String PhotoUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_my, container, false);
        loginState = LoginUtils.islogin(getActivity());
        preferences = getActivity().getSharedPreferences("login", LoginActivity.MODE_PRIVATE);
        initViews(view);
        //已登录
        if( loginState){
            //设置头像
            user_id = preferences.getInt("user_id", 0);
            phoneNumber = preferences.getString("phoneNumber", "");
            //password = preferences.getString("password", "");
            loginState = preferences.getBoolean("loginState", false);
            balance = preferences.getFloat("balance", 0);
            Log.e("持久化的数据", user_id + "::" + phoneNumber + "::" + loginState + "::" + balance);
            setUserPhoto(userPhoto);
            getMoney();
            textView1.setText(phoneNumber);

        }
        //初始化listview
        getData();
        listview.setAdapter(new MyOptionAdapter(list, getActivity()));
        listview.setOnItemClickListener(this);
        return view;
    }


    //判断是否登录，登录则加载网络和本地，先判断本地是否存在
    private void setUserPhoto(ImageView imageView) {
        BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
        String dir = Environment.getExternalStorageDirectory() + "/com.sweethouse.photo";
        File file = new File(dir+"/"+phoneNumber+".png");
             //用户已登录
            //点击头像仍可跳转到userinfo界面，在userinfo判断是否登录，若未登录退出登录按钮Gone
             if(file.exists()){
                 //存在本地缓存，直接从本地加载
                 // 加载本地图片(路径以/开头， 绝对路径)
                 //图片名称要改，应该改成   手机号码.png
                 bitmapUtils.display(imageView,dir+"/"+user_id+".png");
             }else{
                 //不存在本地缓存，直接从服务器加载
                 // 加载网络图片
                 //取出缓存中的user_id,查找url，使用bitmapUtils进行加载
                 user_id = preferences.getInt("user_id", 0);
                 getPhotoFromWeb(user_id);
             }
    }

    //从服务器加载用户头像
    private void getPhotoFromWeb(int user_id) {
        Log.e("用户ID",user_id+"");
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET,
                GlobalContacts.VISON_URL + "/userServlet?method=getUserPhoto&user_id="+user_id,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        PhotoUrl = result;
                        BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
                        bitmapUtils.display(userPhoto, GlobalContacts.VISON_URL+result);
                    }
                    //图片路径加载失败
                    @Override
                    public void onFailure(HttpException e, String s) {
                      Log.e("加载图片失败","气死了。。。。。。。");
                    }
                });
    }

    private void initViews(View view) {
        userPhoto = (ImageView) view.findViewById(R.id.iv_photo);
        //userImage = (ImageButton) view.findViewById(R.id.iv_userImage);
        textView1 = (TextView) view.findViewById(R.id.tv_login);
        textView2 = (TextView) view.findViewById(R.id.tv_balance);
        listview = (ListView) view.findViewById(R.id.lv_myItem);
        ibtn_setting = (ImageButton) view.findViewById(R.id.ibtn_setting);
        ibtn_message = (ImageButton) view.findViewById(R.id.ibtn_message);
        layout1 = (LinearLayout) view.findViewById(R.id.ll_state1);
        layout2 = (LinearLayout) view.findViewById(R.id.ll_state2);
        layout3 = (LinearLayout) view.findViewById(R.id.ll_state3);
        layout4 = (LinearLayout) view.findViewById(R.id.ll_state4);
        layout5 = (LinearLayout) view.findViewById(R.id.ll_state5);

        //给用户头像添加监听事件，跳转到个人信息页面
        userPhoto.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        layout4.setOnClickListener(this);
        layout5.setOnClickListener(this);
        ibtn_setting.setOnClickListener(this);
        ibtn_message.setOnClickListener(this);
    }

    private void getData() {
        MyOptionData d1 = new MyOptionData(R.drawable.ic_my_wallet, "我的钱包");
        MyOptionData d2 = new MyOptionData(R.drawable.ic_my_order, "我的订单");
        MyOptionData d3 = new MyOptionData(R.drawable.ic_my_addr, "收货地址");
        MyOptionData d4 = new MyOptionData(R.drawable.ic_my_advice, "意见反馈");
        MyOptionData d5 = new MyOptionData(R.drawable.ic_my_clean, "清除缓存");

        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        list.add(d5);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent ;
        switch (position){
            case 0 :
                if(loginState) {
                    intent = new Intent(getActivity(), MyWalletActivity.class);
                    intent.putExtra("user_id",user_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1 :
                if(loginState) {
                    intent = new Intent(getActivity(), MyOrderActivity.class);
                    intent.putExtra("user_id", user_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2 :
                if(loginState) {
                    intent = new Intent(getActivity(), MyAddressActivity.class);
                    intent.putExtra("user_id",user_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3 :
                if(loginState) {
                    intent = new Intent(getActivity(), MyAdviceActivity.class);
                    intent.putExtra("user_id",user_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4 :
                Toast.makeText(getActivity(),"清除图片和sharedpreference缓存",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onClick(View view) {
        Intent orderIntent = new Intent(getActivity(),MyOrderActivity.class);
         switch (view.getId()){
             case R.id.iv_photo:
                 if(loginState){
                     Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                     intent.putExtra("user_id",user_id);
                     intent.putExtra("phoneNumber", phoneNumber);
                     intent.putExtra("loginState",loginState);
                     intent.putExtra("photoUrl",PhotoUrl);
                     startActivity(intent);
                 }else{
                     startActivity(new Intent(getActivity(), LoginActivity.class));

                 }
                 break;
             case R.id.tv_login:
                 //登录以后登录/注册换成手机号
                 if(loginState == false){
                     startActivity(new Intent(getActivity(), LoginActivity.class));
                 }
                 break;
             case R.id.tv_balance:
                 if(loginState) {
                     startActivity(new Intent(getActivity(), MyWalletActivity.class));
                 }
                 break;
             case R.id.ll_state1:
                 if(loginState) {
                     orderIntent.putExtra("orderState", 2);
                     startActivity(orderIntent);
                 }else{
                     Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                 }
                 break;
             case R.id.ll_state2:
                 if(loginState) {
                     orderIntent.putExtra("orderState",3);
                     startActivity(orderIntent);
                 }else{
                     Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                 }
                 break;
             case R.id.ll_state3:
                 if(loginState) {
                     orderIntent.putExtra("orderState",4);
                     startActivity(orderIntent);
                 }else{
                     Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                 }
                 break;
             case R.id.ll_state4:
                 if(loginState) {
                     orderIntent.putExtra("orderState",5);
                     startActivity(orderIntent);
                 }else{
                     Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                 }
                 break;
             case R.id.ll_state5:
                 //跳转到退换货页面
                 if(loginState) {
                     startActivity(new Intent(getActivity(), ReturnSalesActivity.class));
                 }else{
                     Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                 }
                 break;
             case R.id.ibtn_setting:

                 break;
             case R.id.ibtn_message:

                 break;
         }
    }


    //从服务器获取钱包余额信息
    private void getMoney() {
        HttpUtils httpUtils = new HttpUtils();
        String url = GlobalContacts.VISON_URL+"/MyWalletServlet?method=getBalanceByUserId&user_id="+123458;
        httpUtils.send(HttpRequest.HttpMethod.GET,
                url, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String jsonString = responseInfo.result;
                        Gson gson = new Gson();
                        wallet = gson.fromJson(jsonString,Wallet.class);
                        /*Message msg = new Message();
                        msg.what = 2;
                        msg.obj = wallet;
                        handler.sendMessage(msg);*/
                        Log.e("wallet success",wallet.toString());
                        textView2.setText("钱包余额：￥"+wallet.getWallet_balance());
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.e("wallet success",".......");
                    }
                });
    }
}
