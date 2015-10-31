package com.gwm.sweethouse.fragment.company;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.DetailCompanyActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.Company;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.protocol.CompanyProtocol;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/24.
 */
public class CompanyFragment extends BaseFragment {
    private LinearLayout llCompany;
    private ArrayAdapter<CharSequence> city_sAdapter,style_Adapter,hot_Adapter;
    private static ArrayList<Company> companies;
    private RelativeLayout rlBack;
    private ProgressBar mProgressBar;
    private ImageButton ibtnSearch;
    private TextView textMore;
    private Spinner spinnerLoc,spinnerStyle,spinnerHot;
    private ListView lvSearch;
    private GridViewWithHeaderAndFooter lvCompany;
    private RefreshLayout mRefreshLayout;
    private CompanyAdapter adapter;
    String strLoc,strStyle,strHot;

    public CompanyFragment() {
        super(R.layout.fragment_companytitle);
    }

    @Override
    protected View createSuccessView() {
        adapter = new CompanyAdapter();
        View view = View.inflate(getActivity(), R.layout.company_refresh, null);
        View footView=View.inflate(getActivity(),R.layout.fragment_com_more,null);
        View headerView=View.inflate(getActivity(),R.layout.content_company,null);
        //    View viewMore=View.inflate(getActivity(),R.layout.fragment_com_more,null);
        ibtnSearch = (ImageButton) headerView.findViewById(R.id.btn_search);
        lvCompany = (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_company);
        spinnerLoc = (Spinner) headerView.findViewById(R.id.sp_loc);
        spinnerStyle= (Spinner) headerView.findViewById(R.id.sp_style);
        mProgressBar = (ProgressBar) footView.findViewById(R.id.load_progress_bar);
        textMore= (TextView) footView.findViewById(R.id.text_more);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        //页面跳转
        lvCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),DetailCompanyActivity.class);
                //向绑定页面传送点击数据
                intent.putExtra("company_id",companies.get(position).getZx_comid());
                startActivity(intent);
            }
        });
        final EditText text = (EditText) headerView.findViewById(R.id.et_search);

        //搜索框点击事件
        ibtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "搜索", Toast.LENGTH_SHORT).show();
                String text1 = text.getText().toString();
                if (TextUtils.isEmpty(text1)) {
                    text1 = "";
                }
                HttpUtils utils = new HttpUtils();
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.COMPANY_URL + text1, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Gson gson = new Gson();
                        ArrayList<Company> newCompanies = gson.fromJson(result, new TypeToken<ArrayList<Company>>() {
                        }.getType());
                        /*if (newCompanies.size()==0){*/
                        /*    lvSearch.setVisibility(View.GONE);*/
                        /*}*/
                        companies.clear();
                        companies.addAll(newCompanies);
                        Log.e("shousuo", "run");
                        //数据改变，重新加载布局
                        adapter.notifyDataSetChanged();

                        Log.e("shousuo", "runagin");
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getActivity(), "获取服务器数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //查询----未完成

        //同城搜索点击事件

        city_sAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.city, android.R.layout.simple_spinner_item);
        city_sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoc.setAdapter(city_sAdapter);
        spinnerLoc.setPromptId(R.string.city_prompt);
        spinnerLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("loc", position + "");
                if (position == 0) {
                    strLoc = "";
                } else {
                    strLoc = spinnerLoc.getSelectedItem().toString();
                }
                HttpUtils utils = new HttpUtils();
                utils.send(HttpRequest.HttpMethod.GET,
                        GlobalContacts.CITY_SEARCH_URL + strLoc,
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                Gson gson = new Gson();
                                ArrayList<Company> newCompanies = gson.fromJson(result, new TypeToken<ArrayList<Company>>() {
                                }.getType());
                                companies.clear();
                                companies.addAll(newCompanies);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {

                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //类型搜索
      /*  style_Adapter = ArrayAdapter.createFromResource(getActivity(), R.array.style, android.R.layout.simple_spinner_item);
        style_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStyle.setAdapter(style_Adapter);
        spinnerStyle.setPromptId(R.string.style_prompt);
        spinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str;
                Log.e("loc", position + "");
                if (position == 0) {
                    str = "";
                } else {
                    str = spinnerLoc.getSelectedItem().toString();
                }
                HttpUtils utils = new HttpUtils();
               utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.STYLE_SEARCH_URL+strLoc+"&searchStyle&style="+strStyle, new RequestCallBack<String>() {
                   @Override
                   public void onSuccess(ResponseInfo<String> responseInfo) {

                       String result = responseInfo.result;
                       Gson gson = new Gson();
                       ArrayList<Company> newCompanies = gson.fromJson(result, new TypeToken<ArrayList<Company>>() {
                       }.getType());
                       companies.clear();
                       companies.addAll(newCompanies);
                       adapter.notifyDataSetChanged();

                   }

                   @Override
                   public void onFailure(HttpException e, String s) {

                   }
               });



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
//价格排序
/*        hot_Adapter = ArrayAdapter.createFromResource(getActivity(), R.array.style, android.R.layout.simple_spinner_item);
        hot_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHot.setAdapter(hot_Adapter);
        spinnerHot.setPromptId(R.string.hot_prompt);
        spinnerHot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              String url;
                HttpUtils utils = new HttpUtils();
                if(url==GlobalContacts.HOT_SEARCH_ASC){
                utils.send(HttpRequest.HttpMethod.GET,GlobalContacts.HOT_SEARCH_ASC)

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */

        //下拉刷新
        //   lvCompany = (GridViewWithHeaderAndFooter) viewRefresh.findViewById(R.id.lv_company);
        lvCompany.addHeaderView(headerView);
        lvCompany.addFooterView(footView);
        mRefreshLayout.setChildView(lvCompany);
        lvCompany.setAdapter(adapter);

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
        //上拉加载中
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


    class CompanyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return companies.size();
        }

        @Override
        public Object getItem(int position) {
            return companies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CompanyViewHolder holder;
            if (convertView == null) {
                holder = new CompanyViewHolder();
                convertView = View.inflate(getActivity(), R.layout.listview_company_detail, null);
                holder.ivCompanyLog = (ImageView) convertView.findViewById(R.id.im_logo);
                holder.tvComName = (TextView) convertView.findViewById(R.id.compa);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                holder.tvExamNum = (TextView) convertView.findViewById(R.id.tv_anlia2);
                holder.tvDesNum = (TextView) convertView.findViewById(R.id.tv_desnum);
                convertView.setTag(holder);
            } else {
                holder = (CompanyViewHolder) convertView.getTag();
            }
            // utils.display(holder.ivGood, GlobalContacts.SERVER_URL + goods.get(position).getProduct_photo());
            BitmapUtils utils = new BitmapUtils(getActivity());
            utils.display(holder.ivCompanyLog, GlobalContacts.SERVER_URL + companies.get(position).getZx_comlog());
            // holder.tvGoodPrice.setText("￥" + goods.get(position).getProduct_price());
            holder.tvComName.setText(companies.get(position).getZx_comName());
            holder.tvDesNum.setText(companies.get(position).getZx_comdesigner() + "人");
            holder.tvExamNum.setText(companies.get(position).getZx_comexample());
            holder.tvPrice.setText("￥" + companies.get(position).getZx_comaprice());
            return convertView;
        }
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
        CompanyProtocol protocol = new CompanyProtocol(GlobalContacts.COMPANY_URL);
        companies = protocol.loadData();
//        Log.e("ggg", companies.toString());
        if (CompanyFragment.companies == null) {
            return LoadResult.error;
        } else {
            if (CompanyFragment.companies.size() == 0) {
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }
    }

    class CompanyViewHolder {
        ImageView ivCompanyLog;
        TextView tvComName, tvPrice, tvExamNum, tvDesNum;
    }
}
