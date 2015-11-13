package com.gwm.sweethouse.fragment.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.gwm.sweethouse.MallActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.SearchActivity;
import com.gwm.sweethouse.bean.SubClass;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.protocol.MallZmProtocol;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.view.NoFullScreenViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/15.
 */
public class MallFragment extends BaseFragment {

    private NoFullScreenViewPager vpSubClass;
    private FragmentManager fm;
    private RadioGroup rgClass;
    private ImageView ivBack;
    private RelativeLayout rlSearch;

    private ArrayList<ArrayList<SubClass>> subClasses = new ArrayList<>();

    String[] mUrls = new String[]{GlobalContacts.MALL_ZM_URL, GlobalContacts.MALL_WY_URL};
    String[] mCacheDirs = new String[] {"mall_zm", "mall_wy"};
    private View view;

    public MallFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pager_mall;
    }


    @Override
    protected void initFragment(View view) {
        ivBack = (ImageView) view.findViewById(R.id.iv_sort_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected LoadResult load() {
        MallZmProtocol protocol;
        for (int i = 0; i < mUrls.length; i++){
            protocol = new MallZmProtocol(mUrls[i], mCacheDirs[i]);
            LogUtils.i("mUrl:" + mUrls[1]);
            subClasses.add(protocol.loadData());
        }
        LogUtils.i("subClasses:" + subClasses);
        if (subClasses.get(0) == null){
            return LoadResult.error;
        } else {
            if (subClasses.get(0).size() == 0){
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }
    }

    @Override
    protected View createSuccessView() {
        view = View.inflate(context, R.layout.fragment_mall, null);
        vpSubClass = (NoFullScreenViewPager) view.findViewById(R.id.vp_subclass);
        rgClass = (RadioGroup) view.findViewById(R.id.rg_class);
        rlSearch = (RelativeLayout) view.findViewById(R.id.rl_mall_search);
        fm = ((MallActivity) context).getSupportFragmentManager();
        vpSubClass.setAdapter(new VpSubClassAdapter(fm));

        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        rgClass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_zm:
                        vpSubClass.setCurrentItem(0, false);
                        break;
                    case R.id.rb_wy:
                        vpSubClass.setCurrentItem(1, false);
                        break;
                    case R.id.rb_chw:
                        vpSubClass.setCurrentItem(2, false);
                        break;
                    case R.id.rb_kw:
                        vpSubClass.setCurrentItem(3, false);
                        break;
                    case R.id.rb_wj:
                        vpSubClass.setCurrentItem(4, false);
                        break;
                    case R.id.rb_jjrzh:
                        vpSubClass.setCurrentItem(5, false);
                        break;
                    case R.id.rb_jjyp:
                        vpSubClass.setCurrentItem(6, false);
                        break;

                }
            }
        });
        return view;
    }

    class VpSubClassAdapter extends FragmentStatePagerAdapter {

        public VpSubClassAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("subClass", subClasses.get(0));
                    ZmFragment fragment = new ZmFragment();
                    fragment.setArguments(bundle);
                    return fragment;
                default:
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("subClass", subClasses.get(1));
                    WyFragment fragment1 = new WyFragment();
                    fragment1.setArguments(bundle1);
                    return fragment1;
            }
        }

        @Override
        public int getCount() {
            return rgClass.getChildCount();
        }
    }
}
