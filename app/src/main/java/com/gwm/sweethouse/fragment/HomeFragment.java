package com.gwm.sweethouse.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.gwm.sweethouse.DetailsActivity;
import com.gwm.sweethouse.GroupBuyActivity;
import com.gwm.sweethouse.MallActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.SaleActivity;
import com.gwm.sweethouse.SearchActivity;
import com.gwm.sweethouse.adapter.HomePagerContentAdapter;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.CityModel;
import com.gwm.sweethouse.bean.ProvinceModel;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.manager.ThreadManager;
import com.gwm.sweethouse.protocol.HomeProtocol;
import com.gwm.sweethouse.utils.FilesUtils;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.utils.PrefUtils;
import com.gwm.sweethouse.utils.UiUtils;
import com.gwm.sweethouse.utils.XmlParserHandler;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import widget.OnWheelChangedListener;
import widget.WheelView;
import widget.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2015/9/28.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, OnWheelChangedListener {

    /*选择地址相关参数  开始*/
    private WheelView mViewProvince;
    private WheelView mViewCity;
    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    /*选择地址相关参数  结束*/


    private static final int[] images = new int[]{R.mipmap.top1, R.mipmap.top2, R.mipmap.top1};
    private Handler handler;

    private LocationClient mLocationClient = null;
    private BDLocationListener mBDLocationListener = new MyLocationListener();
    private CirclePageIndicator mIndicator;
    private ViewPager vpTop;
    private GridViewWithHeaderAndFooter lvRecommend;
    private RefreshLayout mRefreshLayout;
    private TextView textMore, tvArea;
    private ProgressBar mProgressBar;
    private RadioButton btnMall, btnTuanGou, btnSale, btnZhX;
    private static ArrayList<Recommend> recommends;
    private RelativeLayout rlSearch;
    private int pageNo = 1;
    private String dirTime, onloadDir;
    private GridViewAdapter mAdapter;
    private Button btnConfirm;
    private View dialogView;
    private AlertDialog.Builder dialog;

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[newValue];
        }
    }

    class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String addrStr = bdLocation.getAddrStr();

            if (!TextUtils.isEmpty(addrStr)) {
                final String str = addrStr.substring(addrStr.indexOf("省") + 1, addrStr.indexOf("市"));
                String area = tvArea.getText().toString();
                if (!area.equals(str)){
                   new AlertDialog.Builder(getActivity()).setTitle("定位").setMessage("当前定位：" + str).
                           setPositiveButton("确定", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   tvArea.setText(str);
                                   PrefUtils.setString(getActivity(), "area", str);
                                   dialog.dismiss();
                               }
                           }).setNegativeButton("取消", null).create().show();
                    mLocationClient.stop();
                } else {
                    mLocationClient.stop();
                }
            } else {
                mLocationClient.stop();
            }
        }
    }
    public HomeFragment() {
        super(R.layout.pager_home);
        recommends = new ArrayList<>();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void initFragment(View view) {
        rlSearch = (RelativeLayout) view.findViewById(R.id.rl_search);
        tvArea = (TextView) view.findViewById(R.id.tv_area);
        //
        dialogView = View.inflate(getActivity(), R.layout.area_dialog_view, null);
        setUpViews();
        setUpListener();
        setUpData();
        dialog = new AlertDialog.Builder(getActivity()).setTitle("选择地址").setView(dialogView).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "111:"+mCurrentProviceName+","+mCurrentCityName, Toast.LENGTH_SHORT).show();
                tvArea.setText(mCurrentCityName);
                PrefUtils.setString(getActivity(), "area", mCurrentCityName);
                ((ViewGroup) dialogView.getParent()).removeView(dialogView);
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ViewGroup) dialogView.getParent()).removeView(dialogView);
                dialog.dismiss();
            }
        });
        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        String spArea = PrefUtils.getString(getActivity(), "area", "");
        if (!TextUtils.isEmpty(spArea)){
            tvArea.setText(spArea);
        }
        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.create().show();
            }
        });
    }

    /*选择地址相关方法  开始*/
    private void setUpViews() {
        mViewProvince = (WheelView) dialogView.findViewById(R.id.id_province);
        mViewCity = (WheelView) dialogView.findViewById(R.id.id_city);
    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), mProvinceDatas));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        updateCities();
    }
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), cities));
        mViewCity.setCurrentItem(0);
    }

    protected void initProvinceDatas()
    {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getActivity().getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                }
            }
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
    /*选择地址相关方法  结束*/

    public LoadResult load() {

        HomeProtocol protocol = new HomeProtocol(GlobalContacts.RECOMMEND_URL + pageNo, "recommend");
//        recommends = new ArrayList<>();
        recommends = protocol.loadData();
        if (recommends == null){
            return LoadResult.error;
        } else {
            if (recommends.size() == 0){
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }
    }

    public View createSuccessView() {


        mAdapter = new GridViewAdapter(recommends, getActivity());
        View headerView = View.inflate(getActivity(), R.layout.pager_home_content_header, null);
        View view = View.inflate(getActivity(), R.layout.pager_home_content, null);
        View footerView = View.inflate(getActivity(), R.layout.listview_footer, null);
        textMore = (TextView) footerView.findViewById(R.id.text_more);
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.load_progress_bar);
        btnMall = (RadioButton) headerView.findViewById(R.id.shangcheng);
        btnTuanGou = (RadioButton) headerView.findViewById(R.id.tuangou);
        btnSale = (RadioButton) headerView.findViewById(R.id.sale);
        btnZhX = (RadioButton) headerView.findViewById(R.id.search_zhx);
        btnZhX.setOnClickListener(this);
        btnTuanGou.setOnClickListener(this);
        btnSale.setOnClickListener(this);
        btnMall.setOnClickListener(this);
        if (handler == null){
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    int currentItem = vpTop.getCurrentItem();
                    if (currentItem < images.length - 1){
                        currentItem++;
                    } else {
                        currentItem = 0;
                    }
                    vpTop.setCurrentItem(currentItem);
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
            };
            handler.sendEmptyMessageDelayed(0, 3000);
        }

        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        lvRecommend = (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_recommend);
        lvRecommend.addHeaderView(headerView);
        lvRecommend.addFooterView(footerView);
        mRefreshLayout.setChildView(lvRecommend);
//        if (recommends.size() != 0){
            lvRecommend.setAdapter(mAdapter);
//        }
        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                textMore.setText("上拉加载更多");
                mRefreshLayout.setLoading(false);
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        pageNo = 1;
                        final String dirTime1 = System.currentTimeMillis() + "";
                        HomeProtocol protocol = new HomeProtocol(GlobalContacts.RECOMMEND_URL + pageNo, dirTime1);
                        final ArrayList<Recommend> recommends_refresh = protocol.loadData();
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (recommends_refresh != null) {
                                    File dir = FilesUtils.getCacheDri();
                                    if (dirTime != null){
                                        File file = new File(dir, dirTime);
                                        if (file.isFile() && file.exists()) {
                                            file.delete();
                                        }
                                        dirTime = dirTime1;
                                    }
                                    recommends.clear();
                                    recommends.addAll(recommends_refresh);
                                    LogUtils.d("aaaaa" + recommends);
                                    mAdapter.notifyDataSetChanged();
                                }
                                mRefreshLayout.setRefreshing(false);

                                textMore.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        //使用自定义的RefreshLayout加载更多监听
        //use customed RefreshLayout OnLoadListener
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                LogUtils.d("上拉啦！！！！！");
                textMore.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        final String dirTime1 = System.currentTimeMillis() + "";
                        int i = recommends.size() / (pageNo * GlobalContacts.PAGE_SIZE);
                        ArrayList<Recommend> recommends_onload = null;
                        if (i == 1){
                            pageNo++;
                            HomeProtocol protocol = new HomeProtocol(GlobalContacts.RECOMMEND_URL + pageNo, dirTime1);
                            recommends_onload = protocol.loadData();
                        }
                        final ArrayList<Recommend> finalRecommends_onload = recommends_onload;
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalRecommends_onload == null) {
                                    textMore.setText("已加载全部数据");
                                    textMore.setVisibility(View.VISIBLE);
                                    mProgressBar.setVisibility(View.GONE);
                                }else {
                                    if (finalRecommends_onload.size() != 0) {
                                        File dir = FilesUtils.getCacheDri();
                                        if (onloadDir != null) {
                                            File file = new File(dir, onloadDir);
                                            if (file.isFile() && file.exists()) {
                                                file.delete();
                                            }
                                            onloadDir = dirTime1;
                                        }
                                        recommends.addAll(finalRecommends_onload);
                                        LogUtils.d("aaaaa" + recommends);
                                        mRefreshLayout.setLoading(false);;
                                        mAdapter.notifyDataSetChanged();

                                        textMore.setVisibility(View.VISIBLE);
                                        mProgressBar.setVisibility(View.GONE);
                                    } else {
                                        textMore.setText("已加载全部数据");
                                        textMore.setVisibility(View.VISIBLE);
                                        mProgressBar.setVisibility(View.GONE);
                                    }
                                }
                                Toast.makeText(getActivity(), "加载完成!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        lvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("goodsId", recommends.get(position).getProduct_id());
                startActivity(intent);
            }
        });
        vpTop = (ViewPager) headerView.findViewById(R.id.vp_top);
        vpTop.setAdapter(new HomePagerContentAdapter(images, getActivity()));
        mIndicator = (CirclePageIndicator)headerView.findViewById(R.id.indicator);
        mIndicator.setViewPager(vpTop);
        System.out.println("成功界面创建了");


        mLocationClient = new LocationClient(getActivity());
        mLocationClient.registerLocationListener(mBDLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        return view;
    }

    private void RefreshData() {

    }

    class GridViewAdapter extends MyBaseAdapter<Recommend> {

        public GridViewAdapter(ArrayList<Recommend> datas, Context context) {
            super(datas, context);
        }

        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
            RecommendViewHolder holder;
            if (convertView == null){
                convertView = View.inflate(getActivity(), R.layout.item_list_reconmmend, null);
                holder = new RecommendViewHolder();
                holder.ivRecommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
                holder.tvRecommendTitle = (TextView) convertView.findViewById(R.id.tv_recommend_title);
                holder.tvRecommendPrice = (TextView) convertView.findViewById(R.id.tv_recommend_price);
                convertView.setTag(holder);
            } else {
                holder = (RecommendViewHolder) convertView.getTag();
            }
            Recommend recommend = recommends.get(position);
            utils.display(holder.ivRecommend, GlobalContacts.SERVER_URL + recommend.getProduct_photo());
            holder.tvRecommendTitle.setText(recommend.getProduct_name() + "[" +
                    recommend.getProduct_desc() + "]");
            holder.tvRecommendPrice.setText("￥ " + recommend.getProduct_price());
            return convertView;
        }
    }
    class RecommendViewHolder{
        TextView tvRecommendTitle, tvRecommendPrice;
        ImageView ivRecommend;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shangcheng:
                startActivity(new Intent(getActivity(), MallActivity.class));
                break;
            case R.id.tuangou:
                String area = tvArea.getText().toString();
                if (TextUtils.isEmpty(area)){
                    Toast.makeText(getActivity(), "请选择城市", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), GroupBuyActivity.class);
                    intent.putExtra("area", area);
                    startActivity(intent);
                }
                break;
            case R.id.sale:
                startActivity(new Intent(getActivity(), SaleActivity.class));
                break;
            case R.id.search_zhx:
                Toast.makeText(getActivity(), "进入装修界面", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
