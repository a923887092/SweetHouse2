package com.gwm.sweethouse.fragment.company;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gwm.sweethouse.CodePicDetailActivity;
import com.gwm.sweethouse.OrderZxActivity;
import com.gwm.sweethouse.PicDetailActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.BeautifulPic;
import com.gwm.sweethouse.bean.CodePicDetail;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.protocol.CodePicDetailProtocal;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

public class PicCodeDetailFragment extends BaseFragment {
    private ViewPager vpTop;
    private TextView tvDetail;
    private Button btnOrder;
    private RelativeLayout rlBack;
    private static ArrayList<CodePicDetail> codepic;
    private BitmapUtils utils;
    private CodePicDetailAdapter adapter;
    private FragmentCallBack mCallBack;
    private int i;

    public PicCodeDetailFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_codepic_detail;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack=(CodePicDetailActivity)activity;
    }

    @Override
    protected void initFragment(View view) {
        rlBack = (RelativeLayout) view.findViewById(R.id.rl_back);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected LoadResult load() {
        i=mCallBack.callbackFun(null);
        Log.e("btt",i+"");
        CodePicDetailProtocal protocal = new CodePicDetailProtocal(GlobalContacts.CODEPIC_DETAIL_URL+i, "codedetail"+i);
        codepic = protocal.loadData();

        if (PicCodeDetailFragment.codepic == null) {
            return LoadResult.error;
        } else {
            if (PicCodeDetailFragment.codepic.size() == 0) {
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }
    }

    @Override
    protected View createSuccessView() {
        Log.e("btt","viewSuccess");
        View view = View.inflate(context, R.layout.codepic_content, null);
        vpTop = (ViewPager) view.findViewById(R.id.vp_pic);
//        tvDetail = (TextView) view.findViewById(R.id.tv_detail);
        adapter = new CodePicDetailAdapter();

        vpTop.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    class CodePicDetailAdapter extends PagerAdapter {
        public CodePicDetailAdapter() {
            utils = new BitmapUtils(context);
        }

        @Override
        public int getCount() {
            return codepic.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e("btt","adapterSuccess");
            View view = View.inflate(getActivity(), R.layout.view_pager_item, null);
            ImageView ivPic = (ImageView) view.findViewById(R.id.iv_pic);
            TextView tvDetail = (TextView) view.findViewById(R.id.tv_detail);
            utils.display(ivPic, GlobalContacts.SERVER_URL + codepic.get(position).getCod_pic());
            tvDetail.setText(codepic.get(position).getCod_detail());
            btnOrder= (Button) view.findViewById(R.id.btn_yuyue);
            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), OrderZxActivity.class);
                    intent.putExtra("picOrderId", i);
                    startActivity(intent);
                }
            });
            Log.e("btt",(GlobalContacts.SERVER_URL + codepic.get(position).getCod_pic()));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
