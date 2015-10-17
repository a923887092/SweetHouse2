


package com.gwm.sweethouse.adapter;

        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.gwm.sweethouse.R;
        import com.gwm.sweethouse.bean.OrderListBean;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Administrator on 2015/10/16.
 */
public class OrderListAdapter extends BaseAdapter{
    List<OrderListBean> list=new ArrayList<OrderListBean>();
    Context context;
    LayoutInflater inflater;
    viewHolder vHolder;


    public OrderListAdapter(Context context, List<OrderListBean> list) {
        Log.e("444444","adapter");
        this.context = context;
        this.list = list;
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view==null){
            view=inflater.inflate(R.layout.myorder_item,null);
            vHolder=new viewHolder();
            vHolder.goodsNameView= (TextView) view.findViewById(R.id.goodsname);
            vHolder.goodsDesView= (TextView) view.findViewById(R.id.goodsdes);
            vHolder.goodsPriceView= (TextView) view.findViewById(R.id.goodPrice);
            vHolder.goodsImageView= (ImageView) view.findViewById(R.id.goodsSrc);
            view.setTag(vHolder);
        }else {
            vHolder= (viewHolder) view.getTag();
        }
        int imagesrc=list.get(position).getImagesrc();
        vHolder.goodsImageView.setImageResource(imagesrc);
        vHolder.goodsPriceView.setText(list.get(position).getPrice() + "");
        vHolder.goodsNameView.setText(list.get(position).getGoodsname());
        vHolder.goodsDesView.setText(list.get(position).getGoodsDescribe());


        return view;
    }



    private class viewHolder{
        private TextView goodsNameView,goodsPriceView,goodsDesView,priceView;
        private ImageView goodsImageView;
        private Button orderReviewButton,orderDeleteButton;

    }
}

