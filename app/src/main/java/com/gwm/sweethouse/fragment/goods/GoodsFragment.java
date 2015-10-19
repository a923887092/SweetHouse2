package com.gwm.sweethouse.fragment.goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.GoodsActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.SearchActivity;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.bean.TopicLabelObject;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.protocol.GoodsProtocol;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.view.DropdownButton;
import com.gwm.sweethouse.view.DropdownItemObject;
import com.gwm.sweethouse.view.DropdownListView;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/15.
 */
public class GoodsFragment extends BaseFragment {


    private ImageView ivBack;
    private ArrayList<Product> products = new ArrayList<>();
    private FragmentCallBack mFragmentCallBack;
    private TextView textMore;
    private ProgressBar mProgressBar;
    private GridViewWithHeaderAndFooter lvGoods;
    private RefreshLayout mRefreshLayout;
    private static ArrayList<Product> goods;
    private RelativeLayout rlGoodsSearch;
    public GoodsFragment() {
        super(R.layout.pager_goods);
        /*Bundle arguments = getArguments();
        sortId = arguments.getInt("xl");*/
//        sortId = (int) getActivity().getIntent().getSerializableExtra("xl");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentCallBack = (GoodsActivity) activity;
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
    protected LoadResult load() {
        int sortId = mFragmentCallBack.callbackFun(null);
//        System.out.println("++++++++++++++_______-" + a);
        GoodsProtocol protocol = new GoodsProtocol(GlobalContacts.GOODS_ZM_URL + sortId, "goods_zm");
        LogUtils.i("mUrl:" + GlobalContacts.GOODS_ZM_URL);
        goods = protocol.loadData();
        LogUtils.i("goods:" + goods);
//        if (this.products == null) {
//            return LoadResult.error;
//        } else {
//            if (this.products.size() == 0) {
//                return LoadResult.empty;
//            } else {
//                return LoadResult.success;
//            }
//        }
        return LoadResult.success;
    }

    private static final int ID_PRICE_DEFAULT = 0;
    private static final int ID_PRICE_H_TO_L = 1;
    private static final int ID_PRICE_L_TO_H = 2;
    private static final String PRICE_DEFAULT = "默认排序";
    private static final String PRICE_H_TO_L = "价格从高到底";
    private static final String PRICE_L_TO_H = "价格从底到高";

    private static final int ID_LABEL_ALL = -1;
    private static final String LABEL_ALL = "全部标签";

    private static final String ORDER_REPLY_TIME = "最后评论排序";
    private static final String ORDER_PUBLISH_TIME = "发布时间排序";
    private static final String ORDER_HOT = "热门排序";
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
        View view = View.inflate(getActivity(), R.layout.fragment_goods, null);
        View headerView = View.inflate(getActivity(), R.layout.fragment_goods_header, null);
        View footerView = View.inflate(getActivity(), R.layout.listview_footer, null);
        textMore = (TextView) footerView.findViewById(R.id.text_more);
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.load_progress_bar);

        //给搜索框绑定点击事件  以后要修改
        rlGoodsSearch = (RelativeLayout) headerView.findViewById(R.id.rl_goods_search);
        rlGoodsSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
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
        TopicLabelObject topicLabelObject1 = new TopicLabelObject(1, 1, "Fragment");
        labels.add(topicLabelObject1);
        TopicLabelObject topicLabelObject2 = new TopicLabelObject(2, 1, "CustomView");
        labels.add(topicLabelObject2);
        TopicLabelObject topicLabelObject3 = new TopicLabelObject(2, 1, "Service");
        labels.add(topicLabelObject3);
        TopicLabelObject topicLabelObject4 = new TopicLabelObject(2, 1, "BroadcastReceiver");
        labels.add(topicLabelObject4);
        TopicLabelObject topicLabelObject5 = new TopicLabelObject(2, 1, "Activity");
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
        if (goods.size() != 0){
            lvGoods.setAdapter(new GoodsGridViewAdapter());
            lvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LogUtils.i("跳转到商品详情页面：" + position);
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
        //使用自定义的RefreshLayout加载更多监听
        //use customed RefreshLayout OnLoadListener
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
        return view;
    }

    class GoodsGridViewAdapter extends MyBaseAdapter<Product> {
        public GoodsGridViewAdapter() {
            super(goods, getActivity());
            utils.configDefaultLoadingImage(R.drawable.image1);
        }
        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
            GoodsViewHolder holder;
            if (convertView == null){
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

            utils.display(holder.ivGood, GlobalContacts.SERVER_URL + goods.get(position).getProduct_photo());
            holder.tvGoodTitle.setText("【" + goods.get(position).getProduct_name() + "】" +
                    goods.get(position).getProduct_desc());
            holder.tvGoodPrice.setText("￥" + goods.get(position).getProduct_price());
            holder.goodSaleNum.setText("已售" + goods.get(position).getSaled_num());
            return convertView;
        }
    }
    class GoodsViewHolder{
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
                    item.setSuffix("(5)");
                targetList.add(item);
            }
            updateLabels(targetList);
        }
    }
}
