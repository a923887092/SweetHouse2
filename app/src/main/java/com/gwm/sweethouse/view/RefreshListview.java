package com.gwm.sweethouse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gwm.sweethouse.R;

/**
 * Created by Administrator on 2015/10/29.
 */
public class RefreshListview extends ListView{
    private static final int STATE_PULL_REFRESH=0;
    private static final int STATE_RELEASE_REFRESH=1;
    private static final int STATE_REFRESHING=2;
    View mHeaderView;
    ImageView refresh_arrowiv;
    ProgressBar refresh_pb;
    int mHeaderViewHeight;
    TextView refresh_title;
    private RotateAnimation arrowUp;
    private RotateAnimation arrowDown;
    private int startY=-1;
    private int endY;
    private int dy;

    private int mCurrentState=STATE_PULL_REFRESH;
    private View mFooterView;
    private int mFooterViewHeighr;



    public RefreshListview(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
    }



    public RefreshListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    public RefreshListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
    }

    private void initFooterView() {
        mFooterView = View.inflate(getContext(), R.layout.refresh_listview_footerview, null);
        this.addFooterView(mFooterView);

        mFooterView.measure(0, 0);
        mFooterViewHeighr=mFooterView.getMeasuredHeight();
        //mFooterView.setPadding(0,-mFooterViewHeighr,0,0);
        //this.setOnScrollListener(this);




    }


    private void initHeaderView() {
        mHeaderView=View.inflate(getContext(), R.layout.refresh_listview_headerview,null);
        this.addHeaderView(mHeaderView);
        refresh_arrowiv= (ImageView) mHeaderView.findViewById(R.id.refresh_redarrow);
        refresh_pb= (ProgressBar) mHeaderView.findViewById(R.id.refresh_pb);
        refresh_title= (TextView) mHeaderView.findViewById(R.id.refresh_tv);
        mHeaderView.measure(0, 0);
        mHeaderViewHeight=mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
        initArrowAnim();

    }

    private void initArrowAnim() {
        arrowUp=new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5F,
                Animation.RELATIVE_TO_SELF,0.5F);
        arrowUp.setDuration(500);
        arrowUp.setFillAfter(true);

        arrowDown=new RotateAnimation(-180,0,Animation.RELATIVE_TO_SELF,0.5F,
                Animation.RELATIVE_TO_SELF,0.5F);
        arrowDown.setDuration(500);
        arrowDown.setFillAfter(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY==-1){
                    startY = (int) ev.getRawY();
                }
                if (mCurrentState==STATE_REFRESHING){
                    break;
                }
                endY= (int) ev.getRawY();
                dy = endY-startY;
                //当偏移量大于0且在第一个item时 才显示下拉刷新
                if (dy>0&&getFirstVisiblePosition()==0){
                    int padding=dy-mHeaderViewHeight;

                        mHeaderView.setPadding(0, padding, 0, 0);

                    if (padding>0&&mCurrentState!=STATE_RELEASE_REFRESH){
                        //状态改为松开刷新

                        mCurrentState=STATE_RELEASE_REFRESH;
                        refreshState();
                    }else if (padding<0&&mCurrentState!=STATE_PULL_REFRESH){
                        //状态改为下拉刷新
                        mCurrentState=STATE_PULL_REFRESH;
                        refreshState();
                    }
                    return  true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY=-1;
                if (mCurrentState==STATE_RELEASE_REFRESH){
                    mCurrentState=STATE_REFRESHING;
                    mHeaderView.setPadding(0,0,0,0);
                    refreshState();
                }else if (mCurrentState==STATE_PULL_REFRESH){
                    mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshState() {
        switch (mCurrentState){
            case STATE_PULL_REFRESH:
                refresh_title.setText("下拉刷新");
                refresh_arrowiv.setVisibility(VISIBLE);
                refresh_pb.setVisibility(INVISIBLE);
                refresh_arrowiv.startAnimation(arrowDown);
                break;
            case STATE_REFRESHING:
                refresh_title.setText("正在刷新。。。");
                refresh_arrowiv.clearAnimation();
                refresh_arrowiv.setVisibility(INVISIBLE);
                refresh_pb.setVisibility(VISIBLE);
                if (mlistener!=null){
                mlistener.OnRefresh();
                }
                break;
            case  STATE_RELEASE_REFRESH:
                refresh_title.setText("松开刷新");
                refresh_arrowiv.setVisibility(VISIBLE);
                refresh_pb.setVisibility(INVISIBLE);
                refresh_arrowiv.startAnimation(arrowUp);
                break;

            default:
                break;
        }
    }
    OnRefreshListener mlistener;

    public void setOnRefreshListener(OnRefreshListener listener){
        mlistener=listener;
    }

   /* private  boolean isloading=false;
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_FLING){
                    if (getLastVisiblePosition()==getCount()-1&&!isloading){
                        mFooterView.setPadding(0, 0, 0, 0);
                        setSelection(getCount() - 1);
                        Log.e("快了快了","飞起来了");
                        isloading=true;
                    }
                }
            }





    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
*/
    public interface OnRefreshListener{
        public void OnRefresh();
    }

    public void  onRefreshComplete(){
        /*if (isloading){
            mFooterView.setPadding(0,-mFooterViewHeighr,0,0);
            isloading=true;
        }else {*/
            mCurrentState = STATE_PULL_REFRESH;
            refresh_title.setText("下拉刷新");
            refresh_arrowiv.setVisibility(VISIBLE);
            refresh_pb.setVisibility(INVISIBLE);
            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
       // }
    }

}

