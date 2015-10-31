package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.CartBean;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
public class OrderConfirmationAdapter extends BaseAdapter {
    List<CartBean> list=new ArrayList<CartBean>();
    Context context;
    LayoutInflater inflater;
    ViewHolder viewHolder;

    public OrderConfirmationAdapter(List<CartBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView==null){
           convertView=inflater.inflate(R.layout.item_order_confirmation,null);
           viewHolder=new ViewHolder();
           viewHolder.goodsDestv= (TextView) convertView.findViewById(R.id.goodsDes);
           viewHolder.goodsnametv= (TextView) convertView.findViewById(R.id.goodsname);
           viewHolder.goodspricetv= (TextView) convertView.findViewById(R.id.goodsprice);
           viewHolder.goodsamounttv= (TextView) convertView.findViewById(R.id.goodsamount);
           viewHolder.goodssumtv= (TextView) convertView.findViewById(R.id.goodssum);
           viewHolder.getGoodsamountconfirmtv= (TextView) convertView.findViewById(R.id.goodsamountconfirm);
           viewHolder.imageView= (ImageView) convertView.findViewById(R.id.imagesrc);
           convertView.setTag(viewHolder);

       }else {
           viewHolder= (ViewHolder) convertView.getTag();
       }
        BitmapUtils bitmapUtils=new BitmapUtils(context);
        bitmapUtils.configDefaultLoadingImage(R.drawable.onloading);
        bitmapUtils.display(viewHolder.imageView,list.get(position).getImagesrc());
        viewHolder.goodsDestv.setText(list.get(position).getGoodsDescribe());
        viewHolder.goodsnametv.setText(list.get(position).getGoodsname());
        viewHolder.goodspricetv.setText("¥"+list.get(position).getPrice()+"");
        viewHolder.goodsamounttv.setText("x"+list.get(position).getGoods_amount()+"");
        viewHolder.goodssumtv.setText("合计："+"¥"+list.get(position).getGoods_amount()*list.get(position).getPrice()+"");
        viewHolder.getGoodsamountconfirmtv.setText("共"+list.get(position).getGoods_amount()+"件商品");


        return convertView;
    }
    class ViewHolder{
            ImageView imageView;
        TextView goodsDestv,goodsnametv,goodspricetv,goodsamounttv,
                goodssumtv,getGoodsamountconfirmtv;
    }
}
