package com.gwm.sweethouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.gwm.sweethouse.fragment.mall.WyFragment;
import com.gwm.sweethouse.fragment.mall.ZmFragment;
import com.gwm.sweethouse.view.NoFullScreenViewPager;

public class MallActivity extends FragmentActivity {

    private NoFullScreenViewPager vpSubClass;
    private FragmentManager fm;
    private RadioGroup rgClass;
    private ImageView ivBack;
    private RelativeLayout rlSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        vpSubClass = (NoFullScreenViewPager) findViewById(R.id.vp_subclass);
        rgClass = (RadioGroup) findViewById(R.id.rg_class);
        ivBack = (ImageView) findViewById(R.id.iv_sort_back);
        rlSearch = (RelativeLayout) findViewById(R.id.rl_mall_search);
        fm = getSupportFragmentManager();
        vpSubClass.setAdapter(new VpSubClassAdapter(fm));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MallActivity.this, SearchActivity.class));
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
    }

    class VpSubClassAdapter extends FragmentStatePagerAdapter {

        public VpSubClassAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ZmFragment();
                default:
                    return new WyFragment();
            }
        }

        @Override
        public int getCount() {
            return rgClass.getChildCount();
        }
    }
}
