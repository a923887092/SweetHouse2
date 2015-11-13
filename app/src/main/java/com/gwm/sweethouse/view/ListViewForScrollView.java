package com.gwm.sweethouse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gwm.sweethouse.utils.LogUtils;

/**
 * Created by Administrator on 2015/10/28.
 */
public class ListViewForScrollView extends ListView implements View.OnTouchListener,AbsListView.OnScrollListener {
    private int listViewTouchAction;
    private static final int MAXIMUM_LIST_ITEMS_VIEWABLE = 99;
    private boolean isFirstRow = false;
    private int state;
    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        listViewTouchAction = -1;
        setOnScrollListener(this);
        setOnTouchListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (getAdapter() != null
                && getAdapter().getCount() > MAXIMUM_LIST_ITEMS_VIEWABLE) {
            if (listViewTouchAction == MotionEvent.ACTION_MOVE) {
                scrollBy(0, -1);
            }
        }
        if (firstVisibleItem == 0){
            isFirstRow = true;
        }
    }
    int y;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtils.e("onTouchEvent");

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                y = (int) ev.getY();
                LogUtils.d("onTouchEvent ACTION_DOWN" + "::" + y);
                break;
            case MotionEvent.ACTION_MOVE:
                int nY = (int) ev.getY();
                int offY = nY - y;
                if (offY > 200 && getFirstVisiblePosition()==0){
                    LogUtils.e("onTouchEvent false" + nY + "::" + y);
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    LogUtils.e("onTouchEvent true" + nY + "::" + y);
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (isFirstRow && state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
//            return false;
//        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
       boolean a =  view.getFirstVisiblePosition() == 0;
        state = scrollState;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newHeight = 0;
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            ListAdapter listAdapter = getAdapter();
            if (listAdapter != null && !listAdapter.isEmpty()) {
                int listPosition = 0;
                for (listPosition = 0; listPosition < listAdapter.getCount()
                        && listPosition < MAXIMUM_LIST_ITEMS_VIEWABLE; listPosition++) {
                    View listItem = listAdapter.getView(listPosition, null,
                            this);
                    // now it will not throw a NPE if listItem is a ViewGroup
                    // instance
                    if (listItem instanceof ViewGroup) {
                        listItem.setLayoutParams(new LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT));
                    }
                    listItem.measure(widthMeasureSpec, heightMeasureSpec);
                    newHeight += listItem.getMeasuredHeight();
                }
                newHeight += getDividerHeight() * listPosition;
            }
            if ((heightMode == MeasureSpec.AT_MOST) && (newHeight > heightSize)) {
                if (newHeight > heightSize) {
                    newHeight = heightSize;
                }
            }
        } else {
            newHeight = getMeasuredHeight();
        }
        setMeasuredDimension(getMeasuredWidth(), newHeight);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getAdapter() != null
                && getAdapter().getCount() > MAXIMUM_LIST_ITEMS_VIEWABLE) {
            if (listViewTouchAction == MotionEvent.ACTION_MOVE) {
                scrollBy(0, 1);
            }
        }
        return false;
    }
}
