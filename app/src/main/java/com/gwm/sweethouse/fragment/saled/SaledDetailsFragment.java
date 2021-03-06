package com.gwm.sweethouse.fragment.saled;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.gwm.sweethouse.DetailsActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.base.NewBaseFragment;
import com.gwm.sweethouse.bean.MenuItem;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.bean.ProductImg;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.fragment.details.GoodImgFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.CascadingMenuViewOnSelectListener;
import com.gwm.sweethouse.interfaces.DetailsFragmentCallBack;
import com.gwm.sweethouse.interfaces.FragmentCallBack;
import com.gwm.sweethouse.protocol.DetailsProtocol;
import com.gwm.sweethouse.protocol.GoodsImgProtocol;
import com.gwm.sweethouse.utils.CustomDigitalClock;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.view.CascadingMenuPopWindow;
import com.gwm.sweethouse.view.SelectNumPopupWindow;
import com.gwm.sweethouse.view.snapscrollview.McoyProductContentPage;
import com.gwm.sweethouse.view.snapscrollview.McoyProductDetailInfoPage;
import com.gwm.sweethouse.view.snapscrollview.McoySnapPageLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/30.
 */
public class SaledDetailsFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivBack;
    private RelativeLayout rlNoBuy;
    private LinearLayout llShop;
    private Handler mHandler;
    private int mContent = 1;
    private static ArrayList<Product> goods;
    private ArrayList<ProductImg> productImgs;
    private DetailsFragmentCallBack mFragmentCallBack;
    private List<String> networkImages = new ArrayList<>();
    private TextView tvGoodDesc, tvGoodPrice, tvCount, tvAddress, tvAddressPrice;
    private RelativeLayout rlCount, rlAddress;
    private LinearLayout llComment, llGoBuy;
    private ViewPager vpGood;
    private View view;
    private Button btnBuyOrNo, btnGo;
    private int state;
    private CustomDigitalClock remainTime;
    private TextView tvTitle;
    private View rootView, bottomView, topView;
    private McoySnapPageLayout mcoySnapPageLayout;
    private McoyProductDetailInfoPage topPage;
    private McoyProductContentPage bottomPage;

    public SaledDetailsFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pager_details;
    }

    @Override
    protected void initFragment(View view) {
        state = mFragmentCallBack.callbackState(null);
        btnBuyOrNo = (Button) view.findViewById(R.id.btn_buy_or_no);
        llGoBuy = (LinearLayout) view.findViewById(R.id.ll_Buy);

        rlNoBuy = (RelativeLayout) view.findViewById(R.id.rl_no_buy);
        llShop = (LinearLayout) view.findViewById(R.id.ll_shop);
        llShop.setVisibility(View.GONE);
        rlNoBuy.setVisibility(View.VISIBLE);
        LogUtils.e(state + "77777");
        if (state == 0) {
            btnBuyOrNo.setVisibility(View.GONE);
            llGoBuy.setVisibility(View.VISIBLE);
            remainTime = (CustomDigitalClock) view.findViewById(R.id.remainTime);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            btnGo = (Button) view.findViewById(R.id.btn_go);
            btnGo.setOnClickListener(this);
            startTime();
        }
        ivBack = (ImageView) view.findViewById(R.id.iv_goods_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void startTime() {
        new HttpUtils().send(HttpRequest.HttpMethod.GET, GlobalContacts.SERVER_URL +
                "/saledServlet?method=getTime", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

//                    state = STATE_END;
                remainTime.setEndTime(Long.parseLong(result) + 3600000);
                remainTime.setClockListener(new CustomDigitalClock.ClockListener() { // register the clock's listener

                    @Override
                    public void timeEnd() {
                        // The clock time is ended.
//                            Toast.makeText(getActivity(), "8888888888888", Toast.LENGTH_SHORT).show();
//                            remainTime.setVisibility(View.INVISIBLE);
                        btnBuyOrNo.setVisibility(View.VISIBLE);
                        llGoBuy.setVisibility(View.GONE);
                    }

                    @Override
                    public void remainFiveMinutes() {
                        // The clock time is remain five minutes.
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    protected LoadResult load() {
        int goodsId = mFragmentCallBack.callbackGoodsId(null);
//        System.out.println("++++++++++++++_______-" + a);
        DetailsProtocol protocol = new DetailsProtocol(GlobalContacts.GOOD_URL + goodsId, "good_details" + goodsId);
        goods = protocol.loadData();
        GoodsImgProtocol imgProtocol = new GoodsImgProtocol(GlobalContacts.GOOD_IMG_URL + goodsId, "good_img" + goodsId);
        productImgs = imgProtocol.loadData();
        LogUtils.i("goods:" + goods);
        LogUtils.i("productImgs" + productImgs);
        return LoadResult.success;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Bundle data = msg.getData();
                        mContent = data.getInt("good_num");
                        LogUtils.i("handler:" + mContent);
                        tvCount.setText(mContent + "件" + "（库存" + goods.get(0).getProduct_sum() + "件）");
                }
            }
        };
        mFragmentCallBack = (DetailsActivity) activity;
    }


    @Override
    protected View createSuccessView() {
        topView = View.inflate(context, R.layout.fragment_details, null);
        bottomView = View.inflate(context, R.layout.mcoy_product_content_page, null);
        rootView = View.inflate(context, R.layout.fragment_details_new, null);
        mcoySnapPageLayout = (McoySnapPageLayout) rootView.findViewById(R.id.flipLayout);
        topPage = new McoyProductDetailInfoPage(context,topView);
        bottomPage = new McoyProductContentPage(context, bottomView);
        mcoySnapPageLayout.setSnapPages(topPage, bottomPage);
        //初始化menuItems
        ArrayList<MenuItem> tempMenuItems = null;
        for (int j = 0; j < GroupNameArray.length; j++) {
            tempMenuItems = new ArrayList<MenuItem>();
            for (int i = 0; i < childNameArray[j].length; i++) {
                tempMenuItems.add(new MenuItem(false, childNameArray[j][i], null));
            }
            menuItems.add(new MenuItem(true, GroupNameArray[j], tempMenuItems));
        }

        Product good = goods.get(0);
        /*使用ViewPagerIndicator 的ViewPager*/
        vpGood = (ViewPager) bottomView.findViewById(R.id.vp_good);
        Bundle bundle = new Bundle();
        bundle.putSerializable("productImgs", productImgs);
//        bundle.putSerializable("vpGood", (Serializable) vpGood);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("商品介绍", GoodImgFragment.class, bundle)
                .add("规格参数", GoodImgFragment.class, bundle)
                .create());
        vpGood.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) bottomView.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(vpGood);

        tvGoodDesc = (TextView) topView.findViewById(R.id.good_desc);
        tvGoodPrice = (TextView) topView.findViewById(R.id.good_price);
        tvCount = (TextView) topView.findViewById(R.id.tv_count);
        rlCount = (RelativeLayout) topView.findViewById(R.id.rl_count);
        rlAddress = (RelativeLayout) topView.findViewById(R.id.rl_address);
        llComment = (LinearLayout) topView.findViewById(R.id.ll_comment);
        tvAddress = (TextView) topView.findViewById(R.id.tv_address);
        tvAddressPrice = (TextView) topView.findViewById(R.id.tv_address_price);
        llComment.setOnClickListener(this);
        rlCount.setOnClickListener(this);
        rlAddress.setOnClickListener(this);
        tvGoodDesc.setText("【" + good.getProduct_name() + "】 " + good.getProduct_desc());
        tvGoodPrice.setText("￥" + good.getProduct_price());
        tvCount.setText(mContent + "件" + "（库存" + good.getProduct_sum() + "件）");

        initImageLoader();
        ConvenientBanner convenientBanner = (ConvenientBanner) topView.findViewById(R.id.convenientBanner);
        //网络加载例子
        if (productImgs.size() == 0){
            networkImages.add(GlobalContacts.IMAGE_NULL_URL);
        } else {
            for (ProductImg productImg : productImgs) {
                networkImages.add(GlobalContacts.SERVER_URL + productImg.getImg_url());
            }
        }
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator,
                        R.drawable.ic_page_indicator_focused})
                        //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        //设置翻页的效果，不需要翻页效果可用不设
                .setPageTransformer(ConvenientBanner.Transformer.CubeInTransformer);

        /*SlidingMenu menu = new SlidingMenu(getActivity());
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.fragment_right_menu);*/
        return rootView;
    }


    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    private SelectNumPopupWindow mPopupWindow;
    private CascadingMenuPopWindow cascadingMenuPopWindow = null;
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    String[] GroupNameArray = {"安徽", "江苏"};
    String[][] childNameArray = {
            {"合肥市", "安庆市", "蚌埠市", "亳州市", "池州市", "滁州市", "阜阳市",
                    "淮北市", "淮南市", "黄山市", "六安市", "马鞍山市", "宿州市",
                    "铜陵市", "芜湖市", "宣城市"},
            {"南京市", "常州市", "淮安市", "连云港市", "南通市", "苏州市", "宿迁市",
                    "泰州市", "无锡市", "徐州市", "盐城市", "扬州市", "镇江市"}};
    private String address;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_count:
                mPopupWindow = new SelectNumPopupWindow(getActivity(), goods.get(0), mHandler, mContent);
                mPopupWindow.showAtLocation(getActivity().findViewById(R.id.fl_fragment).findViewById(R.id.fl_content), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                backgroundAlpha(0.5f);
                mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1.0f);
                    }
                });
                break;
            case R.id.rl_address:
                Toast.makeText(getActivity(), "选择收货地址", Toast.LENGTH_SHORT).show();
                int[] location = new int[2];
                v.getLocationInWindow(location);
                if (cascadingMenuPopWindow == null) {
                    cascadingMenuPopWindow = new CascadingMenuPopWindow(getActivity(), menuItems);
                    cascadingMenuPopWindow.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
                }
                cascadingMenuPopWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + v.getWidth() / 10, 0);
                break;
            case R.id.ll_comment:
                Toast.makeText(getActivity(), "进入所有评价", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_go:
                Toast.makeText(getActivity(), "立即购买", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //级联菜单选择回调接口
    class NMCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener {

        @Override
        public void getValue(MenuItem menuItem) {
            Toast.makeText(getActivity(), "" + menuItem.toString(), Toast.LENGTH_SHORT).show();
            tvAddress.setText(menuItem.toString());
        }

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    public class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            imageView.setImageResource(R.drawable.ic_default_adimage);
//            ImageLoader.getInstance().displayImage(data,imageView);
            ImageLoader.getInstance().displayImage(data, imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击事件
                    Toast.makeText(view.getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
