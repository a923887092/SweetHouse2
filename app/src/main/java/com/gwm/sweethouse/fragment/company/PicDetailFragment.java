package com.gwm.sweethouse.fragment.company;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gwm.sweethouse.OrderZxActivity;
import com.gwm.sweethouse.PicDetailActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.BeautifulPic;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.protocol.PicDetailProtocal;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/3.
 */
public class PicDetailFragment extends BaseFragment{
    private FragmentCallBack mCallBack;
    private RelativeLayout rlBack;
    private int i;
    private RefreshLayout mRefreshLayout;
    private TextView textMore;
    private ProgressBar mProgressBar;
    private GridViewWithHeaderAndFooter lvPicDetail;
    private static ArrayList<BeautifulPic> beautPics;
    private PicDetailAdapter adapter;
    private BitmapUtils utils;
    public PicDetailFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pic_detail;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack = (PicDetailActivity) activity;
    }

    @Override
    protected void initFragment(View view) {
        rlBack = (RelativeLayout) view.findViewById(R.id.rl_back);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected LoadResult load() {
        i=mCallBack.callbackFun(null);
        //beautPicDetail+i----缓存--名字是自己定义的，+i是为了缓存的文件名不重复
         PicDetailProtocal protocal=new PicDetailProtocal(GlobalContacts.PIC_DETAIT_URL+i,"beautPicDetail"+i);
        beautPics=protocal.loadData();
             if(PicDetailFragment.beautPics==null){
            return LoadResult.error;
        }else{
            if(PicDetailFragment.beautPics.size()==0){
                return LoadResult.empty;
            }else
            {
                return LoadResult.success;
            }
        }
    }

    @Override
    protected View createSuccessView() {

        utils=new BitmapUtils(context);
        adapter=new PicDetailAdapter();
        View view = View.inflate(getActivity(), R.layout.content_gridview, null);
        lvPicDetail= (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_company);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        //下拉刷新

        mRefreshLayout.setChildView(lvPicDetail);
        lvPicDetail.setAdapter(adapter);
     /*   mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("btt", "onfresh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
//                        mAdapter.notifyDataSetChanged();
                        textMore.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Refresh Finished!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        //上拉加载
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                textMore.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        getNewBottomData();
                        mRefreshLayout.setLoading(false);
//                        mAdapter.notifyDataSetChanged();
                        textMore.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Load Finished!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });*/
        return view;
    }
    class PicDetailAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return beautPics.size();
        }

        @Override
        public Object getItem(int position) {
            return beautPics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PicDetailHolder holder;
            if (convertView==null){
                holder=new PicDetailHolder();
                convertView=View.inflate(getActivity(),R.layout.beautpic_content,null);
                holder.imPic= (ImageView) convertView.findViewById(R.id.im_titlepic);
                holder.tvDetail= (TextView) convertView.findViewById(R.id.tv_picdetail);
                holder.btnPicOdr= (Button) convertView.findViewById(R.id.btn_picorder);
                convertView.setTag(holder);
            }else{
               holder= (PicDetailHolder) convertView.getTag();
            }
               utils.display(holder.imPic,GlobalContacts.SERVER_URL+beautPics.get(position).getBea_pic());
               holder.tvDetail.setText(beautPics.get(position).getBea_detail());
            holder.btnPicOdr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), OrderZxActivity.class);
                    intent.putExtra("picOrderId", i);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
    class PicDetailHolder{
        ImageView imPic;
        TextView tvDetail;
        Button btnPicOdr;
    }
}
