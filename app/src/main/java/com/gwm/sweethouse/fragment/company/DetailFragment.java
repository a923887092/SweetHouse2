package com.gwm.sweethouse.fragment.company;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.DetailCompanyActivity;
import com.gwm.sweethouse.OrderZxActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.ComDetail;
import com.gwm.sweethouse.bean.Company;
import com.gwm.sweethouse.bean.CompanyPic;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.protocol.CompanyPicProtocal;
import com.gwm.sweethouse.protocol.DetailProtocol;
import com.gwm.sweethouse.tools.CircularImageView;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/27.
 */
public class DetailFragment extends BaseFragment {

    private Button btnOrder;
    private DetailAdapter adapter;
    private ProgressBar mProgressBar;
    private RelativeLayout rlBack;
    private TextView tvAddr,tvPhone,tvDetail;
    private ImageView imComTitle;
    private static ArrayList<ComDetail> details;
    private static ArrayList<CompanyPic> pics;
    private TextView textMore;
    private GridViewWithHeaderAndFooter lvDetail;
    private RefreshLayout mRefreshLayout;
    private FragmentCallBack mCallBack;
    DetailHolder holder;
    private BitmapUtils utils;
    private int i;

    public DetailFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_companytitle;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack = (DetailCompanyActivity) activity;
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
        i = mCallBack.callbackFun(null);
        Log.e("pic",i+"");
        DetailProtocol protocol=new DetailProtocol(GlobalContacts.COMPANY_DETAIL_URL+i, "comDetail" + i);
        CompanyPicProtocal protocalPic=new CompanyPicProtocal(GlobalContacts.COMPANYA_URL + i, "CompanyPic" + i);
        pics=protocalPic.loadData();
        details=protocol.loadData();
        Log.e("aaa","11111"+pics);
        if (details == null || details.size() == 0){
            Log.e("aaa", "22222");
        } else {
            Log.e("aaa", "33333"+details);
        }
       /* if(DetailFragment.details==null||DetailFragment.pics==null){
            return LoadResult.error;
        }else{
            if(DetailFragment.details.size()==0||DetailFragment.pics.size()==0){
                return LoadResult.empty;
            }else
            {
                return LoadResult.success;
            }
        }*/
        return LoadResult.success;

    }

    @Override
    protected View createSuccessView() {
        utils=new BitmapUtils(context);
        adapter=new DetailAdapter();
        View view = View.inflate(getActivity(), R.layout.company_refresh1, null);
        View headerView=View.inflate(getActivity(),R.layout.comdetail_content,null);
        imComTitle= (ImageView) headerView.findViewById(R.id.im_titlepic);
        tvDetail= (TextView) headerView.findViewById(R.id.tv_title);
        btnOrder= (Button) headerView.findViewById(R.id.btn_order);

        tvAddr= (TextView) headerView.findViewById(R.id.tv_addr);
        tvPhone= (TextView) headerView.findViewById(R.id.tv_phone);
        tvDetail.setText(details.get(0).getDt_detail());
        tvPhone.setText(details.get(0).getDt_phone());
        tvAddr.setText(details.get(0).getDt_addr());
        utils.display(imComTitle, GlobalContacts.SERVER_URL + details.get(0).getDt_titlepic());
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        View footView=View.inflate(getActivity(),R.layout.fragment_com_more,null);
        textMore = (TextView) footView.findViewById(R.id.text_more);
        mProgressBar= (ProgressBar) footView.findViewById(R.id.load_progress_bar);
        lvDetail= (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_company);
        lvDetail.addHeaderView(headerView);
        lvDetail.setAdapter(adapter);
        //下拉刷新
        lvDetail.addFooterView(footView);
        mRefreshLayout.setChildView(lvDetail);

        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
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
        });
        //装修公司预约点击事件
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),OrderZxActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
    class DetailAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            Log.e("btt",pics.size()+"");
            return pics.size();
        }

        @Override
        public Object getItem(int position) {
            return pics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                holder=new DetailHolder();
                convertView=View.inflate(getActivity(),R.layout.listview_detailcontent,null);
                holder.imPic= (ImageView) convertView.findViewById(R.id.im_pic);
                holder.imDesPic= (CircularImageView) convertView.findViewById(R.id.iv_touxiang);
                holder.tvStyle= (TextView) convertView.findViewById(R.id.tv_style);
                holder.tvDesName= (TextView) convertView.findViewById(R.id.tv_desname);
                holder.tvRoom= (TextView) convertView.findViewById(R.id.tv_roonnum);
                holder.btnOrderDes= (Button) convertView.findViewById(R.id.btn_desOrder);
                convertView.setTag(holder);
            }else {
                holder= (DetailHolder) convertView.getTag();
            }
            utils.display(holder.imPic, GlobalContacts.SERVER_URL + pics.get(position).getCom_pic());
            utils.display(holder.imDesPic,GlobalContacts.SERVER_URL+pics.get(position).getCom_despic());
            holder.tvStyle.setText(pics.get(position).getCom_style());
            holder.tvRoom.setText(pics.get(position).getCom_roomnum());
            holder.tvDesName.setText(pics.get(position).getCom_desname());
            //预约设计师点击事件
            holder.btnOrderDes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),OrderZxActivity.class);
                    intent.putExtra("comOrderId",i);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
    class DetailHolder{
        ImageView imPic,imDesPic;
        TextView tvStyle,tvRoom,tvDesName;
        Button btnOrderDes;
    }
}
