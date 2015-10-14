package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gwm.sweethouse.utils.PrefUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/8 0008.
 */
//欢迎页面
public class GuideActivity extends Activity {
    private ViewPager vpGuide;
    private ArrayList<ImageView> mImageViewList;
    private Button btnStart;
    //获得欢迎页面的三张图片
    private static final int[] myImageIds = new int[]{
            R.mipmap.guide1,
            R.mipmap.guide2,
            R.mipmap.guide3
    };
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);//初始化界面时去掉标题头--但是这一步我已经在Manifest中设置过了
        setContentView(R.layout.activity_guide);
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnStart = (Button) findViewById(R.id.button);
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator1);
        //为按钮提供监听事件
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新sp--表示已经展示了新手引导
                PrefUtils.setBoolean(GuideActivity.this, "is_user_guide_showed", true);
                //跳转至主页面
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
        vpGuide.setAdapter(new GuideAdapter());//初始化界面后来setAdapter
        mIndicator.setViewPager(vpGuide);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //  当前选择页面是否为最后一个页面
                if (position == myImageIds.length - 1) {
                    //显示开启新生活按钮
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return myImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(GuideActivity.this);
            view.setBackgroundResource(myImageIds[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
