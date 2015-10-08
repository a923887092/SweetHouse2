package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.gwm.sweethouse.utils.PrefUtils;

public class SplashActivity extends Activity {

    private RelativeLayout splash;
    private AnimationSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = (RelativeLayout) findViewById(R.id.splash);
        startAnim();
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean flag = PrefUtils.getBoolean(SplashActivity.this, "guide_is_showed", false);
                if (flag){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startAnim() {
        set = new AnimationSet(false);

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);

        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setFillAfter(true);
        scale.setDuration(1000);

        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(1000);
        alpha.setFillAfter(true);

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        splash.startAnimation(set);
    }
}
