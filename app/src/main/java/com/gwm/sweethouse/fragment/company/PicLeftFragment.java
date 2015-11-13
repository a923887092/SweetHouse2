package com.gwm.sweethouse.fragment.company;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.PicDetailActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.BeautifulPic;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.protocol.BeautPicProtocal;
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
 * Created by Administrator on 2015/11/2.
 */
public class PicLeftFragment extends BaseFragment {
    Spinner spanSpace,spanLocal,spanStyle,spanColor;
    private ProgressBar mProgressBar;
    private TextView textMore;
    private RefreshLayout mRefreshLayout;
    private ArrayAdapter<CharSequence> spaceAdapter,localAdapter,styleAdapter,colorAdapter;
    private GridViewWithHeaderAndFooter lvPic;
    String spaceStr,localStr,styleStr,colorStr;
    private BeautiPicAdapter adapter;
    private RelativeLayout rlBack;
    private static ArrayList<BeautifulPic> beautPic;
    private int i;
    public PicLeftFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.content_beautuful_pic;
    }


    @Override
    protected void initFragment(View view) {
        spanSpace= (Spinner)view.findViewById(R.id.sp_space);
        spanLocal= (Spinner) view.findViewById(R.id.sp_local);
        spanColor= (Spinner) view.findViewById(R.id.sp_color);
        spanStyle= (Spinner) view.findViewById(R.id.sp_style);
    }

    @Override
    protected LoadResult load() {
        BeautPicProtocal protocal=new BeautPicProtocal(GlobalContacts.BEAUT_PIC_URL,"Pic");
        beautPic=protocal.loadData();
        if (PicLeftFragment.beautPic == null) {
            return LoadResult.error;
        } else {
            if (PicLeftFragment.beautPic.size() == 0) {
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }
    }

    @Override
    protected View createSuccessView() {


        Log.e("btt", "aa");
        adapter=new BeautiPicAdapter();
        View view = View.inflate(getActivity(), R.layout.company_refresh, null);
        View footView = View.inflate(getActivity(), R.layout.fragment_com_more, null);
        lvPic= (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_company);
        mProgressBar = (ProgressBar) footView.findViewById(R.id.load_progress_bar);
        textMore= (TextView) footView.findViewById(R.id.text_more);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        BitmapUtils utils=new BitmapUtils(getActivity());
        //空间spinner
        spaceAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.space,R.layout.spinner_item);
        spaceAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spanSpace.setAdapter(spaceAdapter);
        spanSpace.setPromptId(R.string.space_prompt);
        spanSpace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("btt","bb");
                if(position==0){
                    spaceStr="";
                }else {
                    spaceStr=spanSpace.getSelectedItem().toString();
                }
                HttpUtils utils=new HttpUtils();
                //http://localhost:8080/SweetHouse/BeatufulPicServlet
                // ?method=picSelect&space=&style=&local=&color=
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.BEAUT_PIC_URL +
                        "&space=" + spaceStr+"&style="+styleStr+"&local="+localStr+"&color="+colorStr
                        , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        Gson gson=new Gson();
                        ArrayList<BeautifulPic> newBeautPic = gson.fromJson(result, new TypeToken<ArrayList<BeautifulPic>>() {
                        }.getType());
                        beautPic.clear();
                        beautPic.addAll(newBeautPic);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getActivity(), "获取服务器数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //局部spanner
        localAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.local,R.layout.spinner_item);
        localAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spanLocal.setAdapter(localAdapter);
        spanLocal.setPromptId(R.string.local_prompt);
        spanLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("btt","cc");
                if(position==0){
                    localStr="";
                }else {
                    localStr=spanLocal.getSelectedItem().toString();
                }
                HttpUtils utils=new HttpUtils();
                //http://localhost:8080/SweetHouse/BeatufulPicServlet
                // ?method=picSelect&space=&style=&local=&color=
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.BEAUT_PIC_URL +
                        "&space=" + spaceStr+"&style="+styleStr+"&local="+localStr+"&color="+colorStr
                        , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        Gson gson=new Gson();
                        ArrayList<BeautifulPic> newBeautPic = gson.fromJson(result, new TypeToken<ArrayList<BeautifulPic>>() {
                        }.getType());
                        beautPic.clear();
                        beautPic.addAll(newBeautPic);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getActivity(), "获取服务器数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //风格spinner
        styleAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.stylePic,R.layout.spinner_item);
        styleAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spanStyle.setAdapter(styleAdapter);
        spanStyle.setPromptId(R.string.stylePic_prompt);
        spanStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    styleStr="";
                }else {
                    styleStr=spanStyle.getSelectedItem().toString();
                }
                HttpUtils utils=new HttpUtils();
                //http://localhost:8080/SweetHouse/BeatufulPicServlet
                // ?method=picSelect&space=&style=&local=&color=
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.BEAUT_PIC_URL +
                        "&space=" + spaceStr+"&style="+styleStr+"&local="+localStr+"&color="+colorStr
                        , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        Gson gson=new Gson();
                        ArrayList<BeautifulPic> newBeautPic = gson.fromJson(result, new TypeToken<ArrayList<BeautifulPic>>() {
                        }.getType());
                        beautPic.clear();
                        beautPic.addAll(newBeautPic);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getActivity(), "获取服务器数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //颜色spanner
        colorAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.color,R.layout.spinner_item);
        colorAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spanColor.setAdapter(colorAdapter);
        spanColor.setPromptId(R.string.color_prompt);
      /*  spanColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    colorStr="";
                }else {
                    colorStr=spanColor.getSelectedItem().toString();
                }
                HttpUtils utils=new HttpUtils();
                //http://localhost:8080/SweetHouse/BeatufulPicServlet
                // ?method=picSelect&space=&style=&local=&color=
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.BEAUT_PIC_URL +
                        "&space=" + spaceStr+"&style="+styleStr+"&local="+localStr+"&color="+colorStr
                        , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        Gson gson=new Gson();
                        ArrayList<BeautifulPic> newBeautPic = gson.fromJson(result, new TypeToken<ArrayList<BeautifulPic>>() {
                        }.getType());
                        beautPic.clear();
                        beautPic.addAll(newBeautPic);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getActivity(), "获取服务器数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });*/
        spanColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    colorStr="";
                }else {
                    colorStr=spanColor.getSelectedItem().toString();
                }
                HttpUtils utils=new HttpUtils();
                //http://localhost:8080/SweetHouse/BeatufulPicServlet
                // ?method=picSelect&space=&style=&local=&color=
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.BEAUT_PIC_URL +
                        "&space=" + spaceStr+"&style="+styleStr+"&local="+localStr+"&color="+colorStr
                        , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        Gson gson=new Gson();
                        ArrayList<BeautifulPic> newBeautPic = gson.fromJson(result, new TypeToken<ArrayList<BeautifulPic>>() {
                        }.getType());
                        beautPic.clear();
                        beautPic.addAll(newBeautPic);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getActivity(), "获取服务器数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//上啦刷新下拉加载
        //下拉刷新
        //   lvCompany = (GridViewWithHeaderAndFooter) viewRefresh.findViewById(R.id.lv_company);
        lvPic.addFooterView(footView);
        mRefreshLayout.setChildView(lvPic);
        lvPic.setAdapter(adapter);
        lvPic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),PicDetailActivity.class);
                intent.putExtra("bea_id",beautPic.get(position).getBea_id());
                startActivity(intent);
            }
        });
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
    class BeautiPicAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return beautPic.size();
        }

        @Override
        public Object getItem(int position) {
            return beautPic.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BeautPicHolder holder;
            if (convertView==null){
                holder=new BeautPicHolder();
                convertView=View.inflate(getActivity(),R.layout.gridview_beautiful_pic,null);
                holder.ivPic= (ImageView) convertView.findViewById(R.id.iv_pic);
                convertView.setTag(holder);
            }else {
                holder = (BeautPicHolder) convertView.getTag();
            }
            BitmapUtils utils=new BitmapUtils(getActivity());
            utils.display(holder.ivPic, GlobalContacts.SERVER_URL + beautPic.get(position).getBea_pic());
            return convertView;
        }

    }
    class BeautPicHolder{
        ImageView ivPic;
    }
}
