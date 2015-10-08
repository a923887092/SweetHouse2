package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.gwm.sweethouse.utils.PrefUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager vpGuide;
    private Button btnEnterHome;
    private ArrayList<ImageView> viewsList;
    private static final int[] imagesArr = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnEnterHome = (Button) findViewById(R.id.btn_enter_home);
        initView();
        vpGuide.setAdapter(new ViewPagerAdapter());
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(vpGuide);
        mIndicator.setOnPageChangeListener(this);
        btnEnterHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.setBoolean(GuideActivity.this, "guide_is_showed", true);

                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void initView(){
        viewsList = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            ImageView image = new ImageView(this);
            image.setImageResource(imagesArr[i]);
            viewsList.add(image);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == viewsList.size() - 1){
            btnEnterHome.setVisibility(View.VISIBLE);
        } else {
            btnEnterHome.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = viewsList.get(position);
            container.addView(view);
            return view;
        }
    }

}
