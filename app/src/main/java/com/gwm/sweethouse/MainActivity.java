package com.gwm.sweethouse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gwm.sweethouse.fragment.CartFragment;
import com.gwm.sweethouse.fragment.HomeFragment;
import com.gwm.sweethouse.fragment.MyFragment;
import com.gwm.sweethouse.fragment.ZhxFragment;

public class MainActivity extends FragmentActivity {

    private RadioButton rbHome, rbZhx, rbCart, rbMy;
    private ViewPager vpContent;
    private RadioGroup rgTab;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        rgTab = (RadioGroup) findViewById(R.id.rg_tab);
        vpContent = (ViewPager) findViewById(R.id.vp_content);
        rbHome = (RadioButton) findViewById(R.id.rb_home);
        rbZhx = (RadioButton) findViewById(R.id.rb_zhx);
        rbCart = (RadioButton) findViewById(R.id.rb_cart);
        rbMy = (RadioButton) findViewById(R.id.rb_my);
        rbHome.setChecked(true);
        vpContent.setAdapter(new ViewPagerAdapter1(fm));


        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        vpContent.setCurrentItem(0, false);
                        break;
                    case R.id.rb_zhx:
                        vpContent.setCurrentItem(1, false);
                        break;
                    case R.id.rb_cart:
                        vpContent.setCurrentItem(2, false);
                        rgTab.setVisibility(View.GONE);
                        break;
                    case R.id.rb_my:
                        vpContent.setCurrentItem(3, false);
                        break;

                }
            }
        });

    }


    class ViewPagerAdapter1 extends FragmentStatePagerAdapter{

        public ViewPagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new  HomeFragment();
            } else if (position == 1){
                return new ZhxFragment();
            } else if (position == 2){
                return new CartFragment();
            } else {
                return new MyFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
