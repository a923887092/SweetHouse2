package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.CartBean;
import com.gwm.sweethouse.fragment.CartFragment;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/19.
 */
public class CartAdapter extends BaseAdapter{
    List<CartBean> list=new ArrayList<CartBean>();
    Context context;
    LayoutInflater inflater;
    ViewHolder viewHolder;
    private  ImageButton amount_addBtn;
    ImageButton amount_subBtn;






    private static ArrayList<Boolean> selectedList;

    public static ArrayList<Boolean> getselectedList() {
        return selectedList;
    }

    public static void setselectedList(ArrayList<Boolean> selectedList) {
        CartAdapter.selectedList = selectedList;
    }





    public CartAdapter(List<CartBean> list, Context context) {
        this.list = list;
        this.context = context;
        if(context!=null) {
            inflater = LayoutInflater.from(context);
        }

        selectedList = new ArrayList<>();
        getselectedList();
        initData();
    }






    private void initData() {
        for(int i=0;i<list.size();i++){

            getselectedList().add(i, false);


        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_cart,null);
            viewHolder=new ViewHolder();

//            viewHolder.goodsamountView= (TextView) convertView.findViewById(R.id.good_amount);
            viewHolder.goodsDesView= (TextView) convertView.findViewById(R.id.goodsDes);
            viewHolder.goodsPriceView= (TextView) convertView.findViewById(R.id.price);
            viewHolder.goodsImageView= (ImageView) convertView.findViewById(R.id.imageSrc);
            viewHolder.goodsCategoryView= (TextView) convertView.findViewById(R.id.goodsCategory);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        amount_addBtn= (ImageButton) convertView.findViewById(R.id.amount_add);
        amount_subBtn= (ImageButton) convertView.findViewById(R.id.amount_sub);
        final CheckBox itemBtn_checked= (CheckBox) convertView.findViewById(R.id.cart_item_checked);
//        CheckBox checkBox = convertView.findViewById()
        //即时刷新的控件不可缓存
        final TextView goodsamountView = (TextView) convertView.findViewById(R.id.good_amount);
        goodsamountView.setText(list.get(position).getGoods_amount() + "");
        BitmapUtils bitmapUtils=new BitmapUtils(context);
        bitmapUtils.configDefaultLoadingImage(R.drawable.onloading);
        bitmapUtils.display(viewHolder.goodsImageView,list.get(position).getImagesrc());
        viewHolder.goodsPriceView.setText("¥" + list.get(position).getPrice() + "");
        viewHolder.goodsCategoryView.setText(list.get(position).getGoodsname());
        viewHolder.goodsDesView.setText(list.get(position).getGoodsDescribe());

        //初始化所有checked为false
        itemBtn_checked.setChecked(getselectedList().get(position));

        itemBtn_checked.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 改变CheckBox的状态
//               itemBtn_checked.toggle();
                //将checkbox的状态纪录下来
                Boolean flag = selectedList.get(position);
                if(flag == true) {
                    itemBtn_checked.setChecked(false);
                    Message msg=new Message();
                    selectedList.set(position, itemBtn_checked.isChecked());
                    msg.what=2;
                    CartFragment.handler.handleMessage(msg);
                } else {
                    itemBtn_checked.setChecked(true);
                    Message msg=new Message();
                    selectedList.set(position, itemBtn_checked.isChecked());
                    msg.what=1;
                    CartFragment.handler.handleMessage(msg);
                }

            }
        });
        //加按钮的点击事件

        amount_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentAmount=list.get(position).getGoods_amount();
                list.get(position).setGoods_amount(currentAmount + 1);
                goodsamountView.setText(currentAmount + 1 + "");
                //判断该item是否处于选中状态 如果是就发送消息 更新sum 如果不是则不发送消息

                Message msg=new Message();
                Bundle data=new Bundle();
                data.putInt("change",1);
                data.putInt("cart_id",list.get(position).getCart_id());
                data.putBoolean("checked",selectedList.get(position));
                msg.setData(data);
                msg.what=3;
                CartFragment.handler.sendMessage(msg);

            }
        });

        //减按钮的点击事件
        amount_subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentAmount=list.get(position).getGoods_amount();

                if (currentAmount==1){
                    Toast.makeText(context, "亲，不能再减咯",Toast.LENGTH_SHORT).show();
                }else{
                    list.get(position).setGoods_amount(currentAmount - 1);
                    goodsamountView.setText(currentAmount - 1 + "");
                }
                //判断该item是否处于选中状态 如果是就发送消息 更新sum 如果不是则不发送消息

                    Message msg=new Message();
                    Bundle data=new Bundle();
                    data.putInt("change",0);
                    data.putInt("cart_id",list.get(position).getCart_id());
                    data.putBoolean("checked",selectedList.get(position));
                    msg.setData(data);
                    msg.what=3;
                    CartFragment.handler.sendMessage(msg);

            }
        });

        return convertView;
    }



    private class ViewHolder{
        private TextView goodsPriceView,goodsDesView,goodsCategoryView;
        private ImageView goodsImageView;


    }
}
