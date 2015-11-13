package com.gwm.sweethouse.fragment.goods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.DetailsActivity;
import com.gwm.sweethouse.GoodsActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.SearchActivity;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.bean.TopicLabelObject;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.manager.ThreadManager;
import com.gwm.sweethouse.protocol.GoodsProtocol;
import com.gwm.sweethouse.protocol.HomeProtocol;
import com.gwm.sweethouse.utils.FilesUtils;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.utils.UiUtils;
import com.gwm.sweethouse.view.DropdownButton;
import com.gwm.sweethouse.view.DropdownItemObject;
import com.gwm.sweethouse.view.DropdownListView;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/15.
 */
public class GoodsFragment extends GoodsBaseFragment {


    private ArrayList<Product> products = new ArrayList<>();
    private int sortId;
    protected FragmentCallBack mFragmentCallBack;
//    GlobalContacts.GOODS_ZM_URL + sortId + "&pageNo=" + pageNo
    @Override
    protected LoadResult load() {
        sortId = mFragmentCallBack.callbackFun(null);
//        System.out.println("++++++++++++++_______-" + a);
        GoodsProtocol protocol = new GoodsProtocol(getUrl(), "goods_zm" + sortId);
        LogUtils.i("mUrl:" + GlobalContacts.GOODS_ZM_URL);
        goods = protocol.loadData();
        LogUtils.i("goods:" + goods);
//        if (this.products == null) {
//            return LoadResult.error;
//        } else {
//            if (this.products.size() == 0) {
//                return LoadResult.empty;
//            } else {
//                return LoadResult.success;
//            }
//        }
        return LoadResult.success;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentCallBack = (GoodsActivity) activity;
    }

    @Override
    protected String getUrl() {
        return GlobalContacts.GOODS_ZM_URL + sortId + "&pageNo=" + pageNo +
                "&productname=" + productName + "&priceOrderId=" + priceOrderId
                + "&priceScreenId=" + priceScreenId + "&salesId=" + salesId;
    }
}
