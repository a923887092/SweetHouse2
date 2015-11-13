package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.Bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/6.
 */
public class BillAdapter extends BaseAdapter {
    List<Bill> list = new ArrayList<Bill>();
    Context context;
    LayoutInflater inflater;
    ViewHolder holder;

    public BillAdapter(Context context, List list){
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
        holder = new ViewHolder();
        if(view == null){
            view = inflater.inflate(R.layout.item_list_order_recorder,null);
            holder.tv_billContent = (TextView) view.findViewById(R.id.tv_billContent);
            holder.tv_billTime = (TextView) view.findViewById(R.id.tv_billTime);
            holder.tv_billMoney = (TextView) view.findViewById(R.id.tv_billMoney);
            holder.iv_billpic = (ImageView) view.findViewById(R.id.iv_billpic);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Date date = list.get(i).getBill_time();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        holder.tv_billTime.setText(time);
        //1-充值记录，2-购买记录，3-退款记录
        if(list.get(i).getBill_state() == 2){
            holder.tv_billMoney.setText("-"+list.get(i).getBill_money()+"￥");
            holder.tv_billContent.setText(list.get(i).getBill_content()+"--购买商品");
        }else if(list.get(i).getBill_state() == 1){
            holder.tv_billMoney.setText("+"+list.get(i).getBill_money()+"￥");
            holder.iv_billpic.setImageResource(R.drawable.ic_my_card);
            holder.tv_billContent.setText(list.get(i).getBill_content() + "--线下充值");
        }else {
            holder.tv_billMoney.setText("+"+list.get(i).getBill_money()+"￥");
            holder.iv_billpic.setImageResource(R.drawable.ic_my_return);
            holder.tv_billContent.setText(list.get(i).getBill_content()+"--订单退款");
        }

        return view;
    }

    class ViewHolder{
        TextView tv_billContent,tv_billTime,tv_billMoney;
        ImageView iv_billpic;
    }
}
