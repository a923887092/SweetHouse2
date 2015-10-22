package com.gwm.sweethouse.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LogUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2015/10/20.
 */
public class SelectNumPopupWindow extends PopupWindow implements View.OnClickListener {
    private View mMenuView;
    private TextView tvPrice, tvCount, tvSale;
    private String content;
    private ImageView ivGood, btnClear;
    private ImageButton btnMin, btnAdd;
    private EditText etNum;
    private int num, maxNum;
    private Handler handler;
    private Context context;
    public SelectNumPopupWindow(Context context, Product good, final Handler handler, int count) {
        super(context);
        this.context = context;
        this.handler = handler;
        maxNum = good.getProduct_sum();
        BitmapUtils utils = new BitmapUtils(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_select_count, null);
        ivGood = (ImageView) mMenuView.findViewById(R.id.iv_good);
        tvPrice = (TextView) mMenuView.findViewById(R.id.tv_price);
        tvCount = (TextView) mMenuView.findViewById(R.id.tv_count);
        tvSale = (TextView) mMenuView.findViewById(R.id.tv_sale);
        btnClear = (ImageView) mMenuView.findViewById(R.id.btn_clear);
        btnAdd = (ImageButton) mMenuView.findViewById(R.id.btn_add);
        btnMin = (ImageButton) mMenuView.findViewById(R.id.btn_min);
        etNum = (EditText) mMenuView.findViewById(R.id.et_num);
        etNum.setText(count + "");
        num = Integer.parseInt(etNum.getText().toString());
        LogUtils.i("parseInt：" + num);
        btnMin.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        tvSale.setText("已售 " + good.getSaled_num() + "笔");
        tvCount.setText("库存" + good.getProduct_sum() + "件");
        tvPrice.setText("￥" + good.getProduct_price());
        utils.display(ivGood, GlobalContacts.SERVER_URL + good.getProduct_photo());
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    private void send(int num){
        LogUtils.i("send:" + num);
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("good_num", num);
        msg.setData(bundle);
        msg.what = 1;
        handler.sendMessage(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                if (num >= maxNum){
                    Toast.makeText(context, "库存不足", Toast.LENGTH_SHORT).show();
                } else {
                    num++;
                    etNum.setText(num + "");
                    send(num);
                }
                break;
            case R.id.btn_min:
                if (num < 2){
                    Toast.makeText(context, "不能再少了", Toast.LENGTH_SHORT).show();
                } else {
                    num--;
                    etNum.setText(num + "");
                    send(num);
                }
                break;
        }
    }
}
