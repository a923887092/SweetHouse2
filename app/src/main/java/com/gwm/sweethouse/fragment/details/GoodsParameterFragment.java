package com.gwm.sweethouse.fragment.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.Parameter;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/9.
 */
public class GoodsParameterFragment extends Fragment {

    private static final String TAG = "GoodsParameterFragment:";
    private View view;
    private TextView tvContent1, tvContent2, tvContent3, tvContent4, tvContent5;
    private int productId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_parameter, null);
        productId = getArguments().getInt("productId", 0);
        tvContent1 = (TextView) view.findViewById(R.id.tv_content1);
        tvContent2 = (TextView) view.findViewById(R.id.tv_content2);
        tvContent3 = (TextView) view.findViewById(R.id.tv_content3);
        tvContent4 = (TextView) view.findViewById(R.id.tv_content4);
        tvContent5 = (TextView) view.findViewById(R.id.tv_content5);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, GlobalContacts.PARAMETER_URL + productId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                ArrayList<Parameter> parameters = gson.fromJson(result, new TypeToken<ArrayList<Parameter>>() {
                }.getType());
                changeUI(parameters);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.d(TAG + "onFailure");
            }
        });
        return view;
    }

    private void changeUI(ArrayList<Parameter> parameters) {
        if (parameters.size() != 0 && parameters != null){
            tvContent1.setText(parameters.get(0).getPara_model());
            tvContent2.setText(parameters.get(0).getPara_func());
            tvContent3.setText(parameters.get(0).getPara_power());
            tvContent4.setText(parameters.get(0).getPara_quality());
            tvContent5.setText(parameters.get(0).getPara_parts());
        }
    }

}
