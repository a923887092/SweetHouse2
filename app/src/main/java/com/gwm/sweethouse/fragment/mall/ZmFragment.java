package com.gwm.sweethouse.fragment.mall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.SubClass;
import com.gwm.sweethouse.global.GlobalContacts;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/14.
 */
public class ZmFragment extends BaseMallFragment {

    private TextView tvSortTitle;

    @Override
    protected void initView(View view) {
        tvSortTitle = (TextView) view.findViewById(R.id.tv_sort_title);
        tvSortTitle.setText("开关照明");
    }

}
