package com.gwm.sweethouse.fragment.search;

import android.app.Activity;
import android.net.Uri;

import com.gwm.sweethouse.GoodsActivity;
import com.gwm.sweethouse.ResultActivity;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.fragment.goods.GoodsBaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.FragmentCallBackString;
import com.gwm.sweethouse.protocol.GoodsProtocol;
import com.gwm.sweethouse.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/26.
 */
public class ResultFragment extends GoodsBaseFragment {
    private final static int STATE_SEARCH = 1;
    private FragmentCallBackString callBackString;
    private String content;
    @Override
    protected LoadResult load() {
        content = callBackString.callback(null);
        productName = content;
        state = STATE_SEARCH;
        LogUtils.d("ResultFragment,load::" + content);
        GoodsProtocol protocol = new GoodsProtocol(getUrl(), "search_goods" + content);
        goods = protocol.loadData();
        if (goods == null) {
            return LoadResult.error;
        } else {
            if (goods.size() == 0) {
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }
    }

    @Override
    protected String getUrl() {
        return GlobalContacts.HOME_SEARCH_GOODS_URL + Uri.encode(content) + "&pageNo=" + pageNo;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBackString = (ResultActivity) activity;
    }
}
