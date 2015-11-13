package com.gwm.sweethouse;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.gwm.sweethouse.fragment.company.PicLeftFragment;
import com.gwm.sweethouse.fragment.company.PicRightFragment;
import com.gwm.sweethouse.interfaces.FragmentCallBack;


public class BeautifulPicActivity extends FragmentActivity implements View.OnClickListener {
    private RadioButton btnLeft,btnRight;
    private RelativeLayout rlBack;
    private ViewPager vpContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beautiful_pic);
        btnLeft = (RadioButton) findViewById(R.id.btn_left);
        btnRight = (RadioButton) findViewById(R.id.btn_right);
        vpContent= (ViewPager) findViewById(R.id.vp_content);
        rlBack= (RelativeLayout) findViewById(R.id.rl_back);
        FragmentManager fm = getSupportFragmentManager();
        vpContent.setAdapter(new ViewPageAdapter(fm));
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_left:
                vpContent.setCurrentItem(0,false);
                break;
            case R.id.btn_right:
                vpContent.setCurrentItem(1,false);

        }
    }


    class ViewPageAdapter extends FragmentStatePagerAdapter{
        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new PicLeftFragment();
            }else{
                return new PicRightFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
