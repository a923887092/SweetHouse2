package com.gwm.sweethouse.fragment.details;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.ProductImg;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.utils.ViewUtil;
import com.lidroid.xutils.BitmapUtils;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/20.
 */
public class GoodImgFragment extends Fragment{

    private ListView lvContent;
    private BitmapUtils utils;
    private ArrayList<ProductImg> productImgs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_good_img, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        utils = new BitmapUtils(view.getContext());
        Bundle bundle = getArguments();
        productImgs = (ArrayList<ProductImg>) bundle.getSerializable("productImgs");
        LogUtils.i("GoodImgFragment" + productImgs);
        lvContent = (ListView) view.findViewById(R.id.lv_content);
        /*int listViewHeight = ViewUtil.setListViewHeightBasedOnChildren1(lvContent);
        ViewGroup.LayoutParams params = view.getParent().getLayoutParams();
        params.height = listViewHeight;
        vpZifuduo.setLayoutParams(params);*/
        lvContent.setAdapter(new ImageAdapter(productImgs, view.getContext()));
    }

    class ImageAdapter extends MyBaseAdapter<ProductImg>{
        private Context context;

        public ImageAdapter(ArrayList<ProductImg> datas, Context context) {
            super(datas, context);
            this.context = context;
        }


        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
            ImageViewHolder holder;
            if (convertView == null){
                convertView = View.inflate(context, R.layout.item_goods_img, null);
                holder = new ImageViewHolder();
                holder.ivImag = (ImageView) convertView.findViewById(R.id.iv_img);
                convertView.setTag(holder);
            } else {
                holder = (ImageViewHolder) convertView.getTag();
            }
            utils.display(holder.ivImag, GlobalContacts.SERVER_URL + datas.get(position).getImg_url());
            return convertView;
        }
    }

    class ImageViewHolder{
        ImageView ivImag;
    }
}
