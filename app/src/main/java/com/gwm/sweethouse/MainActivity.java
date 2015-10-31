package com.gwm.sweethouse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
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
//    private ArrayList<BasePager> viewPagerList;
    private FragmentManager fm;
//  private int[] images = new int[]{R.mipmap.guide_3, R.mipmap.guide_2, R.mipmap.guide_1, R.mipmap.guide_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //
        fm = getSupportFragmentManager();
        rgTab = (RadioGroup) findViewById(R.id.rg_tab);
        vpContent = (ViewPager) findViewById(R.id.vp_content);
        rbHome = (RadioButton) findViewById(R.id.rb_home);
        rbZhx = (RadioButton) findViewById(R.id.rb_zhx);
        rbCart = (RadioButton) findViewById(R.id.rb_cart);
        rbMy = (RadioButton) findViewById(R.id.rb_my);
//        initTabDrawable();
//        initViewPager();
        rbHome.setChecked(true);
        vpContent.setAdapter(new ViewPagerAdapter1(fm));

        //viewpager的滑动监听
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
           //主页面滑动要实现的方法
              /*  switch (position) {
                    case PAGE_HOME:
                        vpContent.setCurrentItem(0, false);
                        break;
                    case PAGE_ZHUANGX:
                        vpContent.setCurrentItem(1, false);
                        break;
                    case TAB_CART:
                        vpContent.setCurrentItem(2, false);
                        break;
                    case TAB_MY:
                        vpContent.setCurrentItem(3, false);
                        break;

                    default:
                        break;
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vpContent.setCurrentItem(0, false);
                        break;
                    case R.id.rb_zhx:
                        vpContent.setCurrentItem(1, false);
                        break;
                    case R.id.rb_cart:
                        vpContent.setCurrentItem(2, false);
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
                //把界面绑定为HomeFragment
                return new HomeFragment();
            } else if(position == 1){
                //把界面绑定为zhuangxFragment
                return new ZhxFragment();
            }else if(position == 2){
                //把界面绑定为CartFragment
                return new CartFragment();
            }else {
                //把界面绑定为MyFragment
                return new MyFragment();
            }
        }

        @Override
        public int getCount() {
            return rgTab.getChildCount();
        }
    }

    /*class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewPagerList.size();
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
//            ImageView image = new ImageView(MainActivity.this);
//            image.setImageResource(images[position]);
//            container.addView(image);
            BasePager view = viewPagerList.get(position);
            container.addView(view.mView);
            return view.mView;
        }
    }*/

    //用户点击返回键，提示是否退出，点击确定，用户退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
           /* // 设置对话框标题
            isExit.setTitle("SweetHouse");*/
            // 设置对话框消息
            isExit.setMessage("您确定要退出吗");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();
        }
        return false;
    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };
}
