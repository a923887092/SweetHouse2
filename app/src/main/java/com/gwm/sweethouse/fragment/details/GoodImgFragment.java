package com.gwm.sweethouse.fragment.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.ProductImg;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LogUtils;
import com.lidroid.xutils.BitmapUtils;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/20.
 */
public class GoodImgFragment extends Fragment{

    private ScrollView svContent;
    private BitmapUtils utils;
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
        ArrayList<ProductImg> productImgs = (ArrayList<ProductImg>) bundle.getSerializable("productImgs");
        LogUtils.i("GoodImgFragment" + productImgs);
        svContent = (ScrollView) view.findViewById(R.id.sv_content);
        LinearLayout ll = new LinearLayout(view.getContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        svContent.addView(ll);
        for (ProductImg img : productImgs){
            ImageView imageView = new ImageView(view.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(20, 10, 20, 10);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            utils.display(imageView, GlobalContacts.SERVER_URL + img.getImg_url());
            ll.addView(imageView);
        }
    }
}
