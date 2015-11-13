package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.CartBean;
import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.BitmapUtils;

import java.text.DecimalFormat;
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
    EditText billet_et;
    static DecimalFormat df= new DecimalFormat("######0.0");
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


    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

           //视图内各控件的初始化
           convertView=inflater.inflate(R.layout.item_order_confirmation,null);
           viewHolder=new ViewHolder();
           viewHolder.goodsDestv= (TextView) convertView.findViewById(R.id.goodsDes);
           viewHolder.goodsnametv= (TextView) convertView.findViewById(R.id.goodsname);
           viewHolder.goodspricetv= (TextView) convertView.findViewById(R.id.goodsprice);
           viewHolder.goodsamounttv= (TextView) convertView.findViewById(R.id.goodsamount);
           viewHolder.goodssumtv= (TextView) convertView.findViewById(R.id.goodssum);
           viewHolder.getGoodsamountconfirmtv= (TextView) convertView.findViewById(R.id.goodsamountconfirm);
           viewHolder.imageView= (ImageView) convertView.findViewById(R.id.imagesrc);
          // viewHolder.billet_et= (EditText) convertView.findViewById(R.id.bill_et);
         //  convertView.setTag(viewHolder);



           //viewHolder= (ViewHolder) convertView.getTag();

//        viewHolder.billet_et.setCursorVisible(true);
        /*viewHolder.billet_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    billet_et.requestFocus();
                    InputMethodManager inputmm= (InputMethodManager) billet_et.getContext().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                }
            }
        });*/
        //两个edittext为了防止视图混乱不放入缓存内
       // billet_et= (EditText) convertView.findViewById(R.id.bill_et);
/*        billet_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });*/
       /* billet_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "亲，不能再减咯", Toast.LENGTH_SHORT).show();
                billet_et.requestFocus();
                InputMethodManager inputmm= (InputMethodManager) billet_et.getContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputmm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });*/
        BitmapUtils bitmapUtils=new BitmapUtils(context);
        bitmapUtils.configDefaultLoadingImage(R.drawable.onloading);
        bitmapUtils.display(viewHolder.imageView, GlobalContacts.VISON_URL + list.get(position).getImagesrc());
        viewHolder.goodsDestv.setText(list.get(position).getGoodsDescribe());
        viewHolder.goodsnametv.setText(list.get(position).getGoodsname());
        if (list.get(position).getProduct_discount()!=0&&list.get(position).getProduct_discount()<1) {

            viewHolder.goodspricetv.setText("¥" + df.format(list.get(position).getPrice() * list.get(position).getProduct_discount()));
            viewHolder.goodssumtv.setText("合计："+"¥"+list.get(position).getGoods_amount()*Float.parseFloat(df.format(list.get(position).getPrice() * list.get(position).getProduct_discount())));
        }else{
            viewHolder.goodspricetv.setText("¥" +df.format(list.get(position).getPrice()));
            viewHolder.goodssumtv.setText("合计："+"¥"+list.get(position).getGoods_amount()*Float.parseFloat(df.format(list.get(position).getPrice() )));
        }
        viewHolder.goodsamounttv.setText("x"+list.get(position).getGoods_amount()+"");

        viewHolder.getGoodsamountconfirmtv.setText("共"+list.get(position).getGoods_amount()+"件商品");


        return convertView;
    }
    class ViewHolder{
            ImageView imageView;
        TextView goodsDestv,goodsnametv,goodspricetv,goodsamounttv,
                goodssumtv,getGoodsamountconfirmtv;
        EditText billet_et,message_et;
    }
}
