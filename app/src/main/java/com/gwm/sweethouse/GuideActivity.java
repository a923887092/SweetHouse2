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

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/8 0008.
 */
//欢迎页面
public class GuideActivity extends Activity {
   private ViewPager vpGuide;
    private LinearLayout llPointGroup;//引导圆点的父控件
    private ArrayList<ImageView> mImageViewList;
    private int mPointWidth;//两个圆点之间的距离
    private View viewRedPoint;//小红点
    private Button btnStart;
    //获得欢迎页面的三张图片
    private static final int[] myImageIds=new int[]{
            R.drawable.guide1,
            R.drawable.guide2,
            R.drawable.guide3
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);//初始化界面时去掉标题头--但是这一步我已经在Manifest中设置过了
        setContentView(R.layout.activity_guide);
        vpGuide= (ViewPager) findViewById(R.id.vp_guide);
        viewRedPoint=findViewById(R.id.view_red_point);
        btnStart= (Button) findViewById(R.id.button);
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
        llPointGroup= (LinearLayout) findViewById(R.id.ll_point_group);
        initView();//初始化界面的方法必须写在初始化控件即findViewById后面
        vpGuide.setAdapter(new GuideAdpter());//初始化界面后来setAdapter
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动事件
                System.out.print("当前位置"+position+";百分比"+positionOffset
               +"移动距离："+positionOffsetPixels );
               int len=(int)(mPointWidth*positionOffset)+position*mPointWidth;
                //获取当前红点的布局参数
                RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
                params.leftMargin=len;//设置左边距
                //重新给小红点设置布局参数
                viewRedPoint.setLayoutParams(params);

            }

            @Override
            public void onPageSelected(int position) {
            //  当前选择页面是否为最后一个页面
                if (position==myImageIds.length-1){
                    //显示开启新生活按钮
                    btnStart.setVisibility(View.VISIBLE);
                }else{
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //初始化引导页界面
    private void initView(){
        mImageViewList=new ArrayList<ImageView>();
        for(int i=0;i<myImageIds.length;i++){
            ImageView image=new ImageView(this);
            //设置引导背景
            image.setBackgroundResource(myImageIds[i]);
            mImageViewList.add(image);
        }
        //初始化界面的小圆点
        for(int i=0;i<myImageIds.length;i++){
            View point=new View(this);
            //设置引导背景
            point.setBackgroundResource(R.drawable.shape_point_gray);//设置引导页小圆点
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(20,20);//设置引导圆点的大小
            if(i>0){
                layoutParams.leftMargin=10;//设置圆点的间隔
            }
            point.setLayoutParams(layoutParams);//设置引导圆点的大小
            llPointGroup.addView(point);//将小圆点添加给线性布局
            //获取视图树--监听事件结束
            llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                //当layout借宿后毁掉此方法
                @Override
                public void onGlobalLayout() {
                    llPointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    mPointWidth=llPointGroup.getChildAt(1).getLeft()-llPointGroup.getChildAt(0).getLeft();
                    System.out.print("圆点距离"+mPointWidth);

                }
            });
        }
    }
    class GuideAdpter extends PagerAdapter {
        @Override
        public int getCount() {
            return myImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
