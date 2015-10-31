package com.gwm.sweethouse.fragment.goods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.DetailsActivity;
import com.gwm.sweethouse.GoodsActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.SearchActivity;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.bean.TopicLabelObject;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.manager.ThreadManager;
import com.gwm.sweethouse.protocol.GoodsProtocol;
import com.gwm.sweethouse.utils.FilesUtils;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.utils.UiUtils;
import com.gwm.sweethouse.view.DropdownButton;
import com.gwm.sweethouse.view.DropdownItemObject;
import com.gwm.sweethouse.view.DropdownListView;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
public abstract class GoodsBaseFragment extends BaseFragment {
    private ImageView ivBack;
    private GoodsGridViewAdapter mAdapter;
    protected static ArrayList<Product> goods = new ArrayList<>();
    protected TextView textMore;
    private ProgressBar mProgressBar;
    private GridViewWithHeaderAndFooter lvGoods;
    private RefreshLayout mRefreshLayout;
    private ImageButton btnSearch;
    protected int pageNo = 1;
    private String dirTime, onloadDir;
    protected String productName = "";
    private EditText etSearch;
    protected int priceOrderId = 0;
    protected int priceScreenId = 8;
    protected int salesId = 51;

    public GoodsBaseFragment() {
        super(R.layout.pager_goods);
        /*Bundle arguments = getArguments();
        sortId = arguments.getInt("xl");*/
//        sortId = (int) getActivity().getIntent().getSerializableExtra("xl");
    }

    protected void checkedGoodsSize(){
        if (goods.size()< GlobalContacts.PAGE_SIZE){
            textMore.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initFragment(View view) {
        ivBack = (ImageView) view.findViewById(R.id.iv_goods_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected abstract LoadResult load();

    protected abstract String getUrl();

    private static final int ID_PRICE_DEFAULT = 0;
    private static final int ID_PRICE_H_TO_L = 1;
    private static final int ID_PRICE_L_TO_H = 2;
    private static final String PRICE_DEFAULT = "默认排序";
    private static final String PRICE_H_TO_L = "价格从高到底";
    private static final String PRICE_L_TO_H = "价格从底到高";

    private static final int ID_LABEL_ALL = 8;
    private static final String LABEL_ALL = "全部价格";

    private static final String ORDER_REPLY_TIME = "销量";
    private static final String ORDER_PUBLISH_TIME = "从高到低";
    private static final String ORDER_HOT = "从低到高";
    private static final int ID_ORDER_REPLY_TIME = 51;
    private static final int ID_ORDER_PUBLISH_TIME = 49;
    private static final int ID_ORDER_HOT = 53;

    ListView listView;
    View mask;
    DropdownButton chooseType, chooseLabel, chooseOrder;
    DropdownListView dropdownType, dropdownLabel, dropdownOrder;

    Animation dropdown_in, dropdown_out, dropdown_mask_out;


    private List<TopicLabelObject> labels = new ArrayList<>();

    private DropdownButtonsController dropdownButtonsController = new DropdownButtonsController();

    @Override
    protected View createSuccessView() {
        mAdapter = new GoodsGridViewAdapter(goods, getActivity());
        View view = View.inflate(getActivity(), R.layout.fragment_goods, null);
        View headerView = View.inflate(getActivity(), R.layout.fragment_goods_header, null);
        View footerView = View.inflate(getActivity(), R.layout.listview_footer, null);
        textMore = (TextView) footerView.findViewById(R.id.text_more);
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.load_progress_bar);

        //给搜索框绑定点击事件  以后要修改
        btnSearch = (ImageButton) headerView.findViewById(R.id.btn_search);
        etSearch = (EditText) headerView.findViewById(R.id.et_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productName = etSearch.getText().toString().trim();
                LogUtils.d("btnSearch onClick!!!");
                refreshData();
            }
        });
        /* 筛选框开始 */
        listView = (ListView) headerView.findViewById(R.id.listView);
        mask = headerView.findViewById(R.id.mask);
        chooseType = (DropdownButton) headerView.findViewById(R.id.chooseType);
        chooseLabel = (DropdownButton) headerView.findViewById(R.id.chooseLabel);
        chooseOrder = (DropdownButton) headerView.findViewById(R.id.chooseOrder);
        dropdownType = (DropdownListView) headerView.findViewById(R.id.dropdownType);
        dropdownLabel = (DropdownListView) headerView.findViewById(R.id.dropdownLabel);
        dropdownOrder = (DropdownListView) headerView.findViewById(R.id.dropdownOrder);



        dropdown_in = AnimationUtils.loadAnimation(getActivity(), R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(getActivity(), R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(getActivity(), R.anim.dropdown_mask_out);

        dropdownButtonsController.init();

        //id count name
        TopicLabelObject topicLabelObject1 = new TopicLabelObject(3, 1, "0~200");
        labels.add(topicLabelObject1);
        TopicLabelObject topicLabelObject2 = new TopicLabelObject(4, 1, "200~400");
        labels.add(topicLabelObject2);
        TopicLabelObject topicLabelObject3 = new TopicLabelObject(5, 1, "400~800");
        labels.add(topicLabelObject3);
        TopicLabelObject topicLabelObject4 = new TopicLabelObject(6, 1, "800~1200");
        labels.add(topicLabelObject4);
        TopicLabelObject topicLabelObject5 = new TopicLabelObject(7, 1, "1200以上");
        labels.add(topicLabelObject5);


        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownButtonsController.hide();
            }
        });


        dropdownButtonsController.flushCounts();
        dropdownButtonsController.flushAllLabels();
        dropdownButtonsController.flushMyLabels();
        /* 筛选框结束 */

        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        lvGoods = (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_recommend);
        lvGoods.addHeaderView(headerView);
        lvGoods.addFooterView(footerView);
        mRefreshLayout.setChildView(lvGoods);
        if (goods.size() != 0) {
            lvGoods.setAdapter(mAdapter);
            lvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LogUtils.i("跳转到商品详情页面：" + position);
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("goodsId", goods.get(position).getProduct_id());
                    startActivity(intent);
                }
            });
        }
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
                        GoodsProtocol protocol = new GoodsProtocol(getUrl(), dirTime1);
                        final ArrayList<Product> recommends_refresh = protocol.loadData();
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (recommends_refresh != null) {
                                    File dir = FilesUtils.getCacheDri();
                                    if (dirTime != null) {
                                        File file = new File(dir, dirTime);
                                        if (file.isFile() && file.exists()) {
                                            file.delete();
                                        }
                                        dirTime = dirTime1;
                                    }
                                    goods.clear();
                                    goods.addAll(recommends_refresh);
                                    LogUtils.d("aaaaa" + goods);
                                    checkedGoodsSize();
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
                        int i = goods.size() / (pageNo * GlobalContacts.PAGE_SIZE);
                        ArrayList<Product> goods_onload = null;
                        if (i == 1) {
                            pageNo++;
                            GoodsProtocol protocol = new GoodsProtocol(getUrl(), dirTime1);
                            goods_onload = protocol.loadData();
                        }

                        final ArrayList<Product> finalGoods_onload = goods_onload;
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalGoods_onload == null) {
                                    textMore.setText("已加载全部数据");
                                    textMore.setVisibility(View.VISIBLE);
                                    mProgressBar.setVisibility(View.GONE);
                                } else {
                                    if (finalGoods_onload.size() != 0) {
                                        File dir = FilesUtils.getCacheDri();
                                        if (onloadDir != null) {
                                            File file = new File(dir, onloadDir);
                                            if (file.isFile() && file.exists()) {
                                                file.delete();
                                            }
                                            onloadDir = dirTime1;
                                        }
                                        goods.addAll(finalGoods_onload);
                                        LogUtils.d("aaaaa" + goods);
                                        mRefreshLayout.setLoading(false);
                                        checkedGoodsSize();
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
        return view;
    }

    private void refreshData() {
        HttpUtils httpUtils = new HttpUtils();
        LogUtils.d("refreshData:" + getUrl());
        httpUtils.send(HttpRequest.HttpMethod.GET, getUrl(), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                ArrayList<Product> myGoods = gson.fromJson(result, new TypeToken<ArrayList<Product>>() {
                }.getType());
                goods.clear();
                goods.addAll(myGoods);
                checkedGoodsSize();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    class GoodsGridViewAdapter extends MyBaseAdapter<Product> {

        public GoodsGridViewAdapter(ArrayList<Product> datas, Context context) {
            super(datas, context);
            utils.configDefaultLoadingImage(R.drawable.image1);
        }


        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
            GoodsViewHolder holder;
            if (convertView == null) {
                holder = new GoodsViewHolder();
                convertView = View.inflate(getActivity(), R.layout.item_list_good, null);
                holder.ivGood = (ImageView) convertView.findViewById(R.id.iv_good);
                holder.tvGoodPrice = (TextView) convertView.findViewById(R.id.tv_good_price);
                holder.tvGoodTitle = (TextView) convertView.findViewById(R.id.tv_good_title);
                holder.goodSaleNum = (TextView) convertView.findViewById(R.id.good_sale_num);
                convertView.setTag(holder);
            } else {
                holder = (GoodsViewHolder) convertView.getTag();
            }

            utils.display(holder.ivGood, GlobalContacts.SERVER_URL + datas.get(position).getProduct_photo());
            holder.tvGoodTitle.setText("【" + datas.get(position).getProduct_name() + "】" +
                    datas.get(position).getProduct_desc());
            holder.tvGoodPrice.setText("￥" + datas.get(position).getProduct_price());
            holder.goodSaleNum.setText("已售" + datas.get(position).getSaled_num());
            return convertView;
        }
    }

    class GoodsViewHolder {
        ImageView ivGood;
        TextView tvGoodTitle, tvGoodPrice, goodSaleNum;
    }


    private class DropdownButtonsController implements DropdownListView.Container {
        private DropdownListView currentDropdownList;
        private List<DropdownItemObject> datasetType = new ArrayList<>(3);//全部讨论
        private List<DropdownItemObject> datasetAllLabel = new ArrayList<>();//全部标签
        private List<DropdownItemObject> datasetMyLabel = new ArrayList<>();//我的标签
        private List<DropdownItemObject> datasetLabel = datasetAllLabel;//标签集合   默认是全部标签
        private List<DropdownItemObject> datasetOrder = new ArrayList<>(3);//评论排序

        @Override
        public void show(DropdownListView view) {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.setVisibility(View.GONE);
                currentDropdownList.button.setChecked(false);
            }
            currentDropdownList = view;
            mask.clearAnimation();
            mask.setVisibility(View.VISIBLE);
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_in);
            currentDropdownList.setVisibility(View.VISIBLE);
            currentDropdownList.button.setChecked(true);
        }

        @Override
        public void hide() {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.button.setChecked(false);
                mask.clearAnimation();
                mask.startAnimation(dropdown_mask_out);
            }
            currentDropdownList = null;
        }

        @Override
        public void onSelectionChanged(DropdownListView view) {
            if (view == dropdownType) {
                updateLabels(getCurrentLabels());
                priceOrderId = view.current.id;
                priceScreenId = 8;
                salesId = 51;
                refreshData();
            } else if (view == dropdownLabel){
                updateLabels(getCurrentLabels());
                priceScreenId = view.current.id;
                priceOrderId = 0;
                salesId = 51;
                refreshData();
            } else if (view == dropdownOrder){
                updateLabels(getCurrentLabels());
                salesId = view.current.id;
                priceOrderId = 0;
                priceScreenId = 8;
                refreshData();
            }

        }

        void reset() {
            chooseType.setChecked(false);
            chooseLabel.setChecked(false);
            chooseOrder.setChecked(false);

            dropdownType.setVisibility(View.GONE);
            dropdownLabel.setVisibility(View.GONE);
            dropdownOrder.setVisibility(View.GONE);
            mask.setVisibility(View.GONE);

            dropdownType.clearAnimation();
            dropdownLabel.clearAnimation();
            dropdownOrder.clearAnimation();
            mask.clearAnimation();
        }

        void init() {
            reset();
            datasetType.add(new DropdownItemObject(PRICE_DEFAULT, ID_PRICE_DEFAULT, "all"));
            datasetType.add(new DropdownItemObject(PRICE_H_TO_L, ID_PRICE_H_TO_L, "my"));
            datasetType.add(new DropdownItemObject(PRICE_L_TO_H, ID_PRICE_L_TO_H, "you"));

            dropdownType.bind(datasetType, chooseType, this, ID_PRICE_DEFAULT);

            datasetAllLabel.add(new DropdownItemObject(LABEL_ALL, ID_LABEL_ALL, null) {
                @Override
                public String getSuffix() {
                    return dropdownType.current == null ? "" : dropdownType.current.getSuffix();
                }
            });
            datasetMyLabel.add(new DropdownItemObject(LABEL_ALL, ID_LABEL_ALL, null));
            datasetLabel = datasetAllLabel;
            dropdownLabel.bind(datasetLabel, chooseLabel, this, ID_LABEL_ALL);

            datasetOrder.add(new DropdownItemObject(ORDER_REPLY_TIME, ID_ORDER_REPLY_TIME, "51"));
            datasetOrder.add(new DropdownItemObject(ORDER_PUBLISH_TIME, ID_ORDER_PUBLISH_TIME, "49"));
            datasetOrder.add(new DropdownItemObject(ORDER_HOT, ID_ORDER_HOT, "53"));
            dropdownOrder.bind(datasetOrder, chooseOrder, this, ID_ORDER_REPLY_TIME);

            dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (currentDropdownList == null) {
                        reset();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        private List<DropdownItemObject> getCurrentLabels() {
            return dropdownType.current != null && dropdownType.current.id == ID_PRICE_H_TO_L ? datasetMyLabel : datasetAllLabel;
        }

        void updateLabels(List<DropdownItemObject> targetList) {
            if (targetList == getCurrentLabels()) {
                datasetLabel = targetList;
                dropdownLabel.bind(datasetLabel, chooseLabel, this, dropdownLabel.current.id);
            }
        }


        public void flushCounts() {

            datasetType.get(ID_PRICE_DEFAULT).setSuffix("");
            datasetType.get(ID_PRICE_H_TO_L).setSuffix("");
            dropdownType.flush();
            dropdownLabel.flush();
        }

        void flushAllLabels() {
            flushLabels(datasetAllLabel);
        }

        void flushMyLabels() {
            flushLabels(datasetMyLabel);
        }

        private void flushLabels(List<DropdownItemObject> targetList) {

            while (targetList.size() > 1) targetList.remove(targetList.size() - 1);
            for (int i = 0, n = 5; i < n; i++) {
                int id = labels.get(i).getId();
                String name = labels.get(i).getName();
                if (TextUtils.isEmpty(name)) continue;
                int topicsCount = labels.get(i).getCount();
                // 只有all才做0数量过滤，因为my的返回数据总是0
                if (topicsCount == 0 && targetList == datasetAllLabel) continue;
                DropdownItemObject item = new DropdownItemObject(name, id, String.valueOf(id));
                if (targetList == datasetAllLabel)
                    item.setSuffix("");
                targetList.add(item);
            }
            updateLabels(targetList);
        }
    }
}
