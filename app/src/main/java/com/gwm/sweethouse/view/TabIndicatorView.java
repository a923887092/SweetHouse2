package com.gwm.sweethouse.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gwm.sweethouse.R;

/**
 * Created by Administrator on 2015/11/6.
 */
public class TabIndicatorView extends LinearLayout {

    private TextView tvTitle, tvNum;
    private View viewLine;
    public TabIndicatorView(Context context) {
        this(context, null);
    }

    public TabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);

   //将布局文件和代码绑定
        View.inflate(context, R.layout.tab_indicator, this);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvNum = (TextView) findViewById(R.id.tv_num);
        viewLine = findViewById(R.id.view_line);
    }

    public void setTabTitle(String title){
        tvTitle.setText(title);
    }

    public void setTabNum(String num){
        tvNum.setText(num);
    }

    public void setTabSelected(boolean selected){
        if (selected){
            tvTitle.setTextColor(Color.RED);
            tvNum.setTextColor(Color.RED);
            viewLine.setBackgroundColor(Color.RED);
        } else {
            tvTitle.setTextColor(Color.BLACK);
            tvNum.setTextColor(Color.BLACK);
            viewLine.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
