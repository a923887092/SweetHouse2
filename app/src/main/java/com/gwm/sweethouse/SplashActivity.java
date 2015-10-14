package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.gwm.sweethouse.utils.PrefUtils;

//闪屏--动画进入
public class SplashActivity extends Activity {
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        linearLayout= (LinearLayout) findViewById(R.id.ll_root);
        startAnim();
    }
    //开启动画
    private void startAnim() {
        AnimationSet set=new AnimationSet(false);//动画集合--旋转和缩放同时进行
        //旋转动画
        RotateAnimation rotate=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);//设置动画时间
        rotate.setFillAfter(true);//保持动画后的状态

        //缩放动画
        ScaleAnimation scale=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);//设置动画时间
        scale.setFillAfter(true);//保持动画状态

        //渐变动画
        AlphaAnimation alpna=new AlphaAnimation(0,1);
        alpna.setDuration(1000);//设置动画时间
        alpna.setFillAfter(true);//保持动画状态

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpna);

        //设置动画监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            //动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        linearLayout.startAnimation(set);
    }

    //跳转至下一个页面
    private void jumpNextPage(){
        //判断是否第一次进入过新手引导页

        //默认不是第一次进入
        boolean userGuide= PrefUtils.getBoolean(this, "is_user_guide_showed", false);
        System.out.println("userGuide" + userGuide);
        if(!userGuide){
            startActivity(new Intent(SplashActivity.this,GuideActivity.class));
        }else{
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }
        finish();
    }


}
