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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.CodePicDetailActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.bean.BeautifulPic;
import com.gwm.sweethouse.bean.CodePic;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.protocol.CodePicProtocal;
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
public class PicRightFragment extends BaseFragment {
    Spinner spanSpace,spanLocal,spanStyle,spanHouse;
    private static ArrayList<CodePic> codePics;
    private CodePicAdapter adapter;
    private GridViewWithHeaderAndFooter lvCodePic;
    private ArrayAdapter<CharSequence> spaceAdapter,localAdapter,styleAdapter,houseAdapter;
    String spaceStr,localStr,styleStr,houseStr;
    private ProgressBar mProgressBar;
    private TextView textMore,tvSpinner;
    private RefreshLayout mRefreshLayout;
    public PicRightFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.content_beaut_morepic;
    }

    @Override
    protected void initFragment(View view) {
        spanSpace= (Spinner)view.findViewById(R.id.sp_space);
        spanLocal= (Spinner) view.findViewById(R.id.sp_local);
        spanHouse= (Spinner) view.findViewById(R.id.sp_house);
        spanStyle= (Spinner) view.findViewById(R.id.sp_style);
    }

    @Override
    protected LoadResult load() {
        CodePicProtocal protocal=new CodePicProtocal(GlobalContacts.CODE_PIC_URL);
        codePics=protocal.loadData();
        if (PicRightFragment.codePics == null) {
            return LoadResult.error;
        } else {
            if (PicRightFragment.codePics.size() == 0) {
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }
    }

    @Override
    protected View createSuccessView() {
        Log.e("btt", "aa");
        adapter=new CodePicAdapter();
        View view = View.inflate(getActivity(), R.layout.company_refresh, null);
        View footView = View.inflate(getActivity(), R.layout.fragment_com_more, null);
        lvCodePic= (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_company);
        mProgressBar = (ProgressBar) footView.findViewById(R.id.load_progress_bar);
        textMore= (TextView) footView.findViewById(R.id.text_more);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        BitmapUtils utils=new BitmapUtils(getActivity());

        //空间spinner
        spaceAdapter= ArrayAdapter.createFromResource(getActivity(), R.array.space, R.layout.spinner_item);
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
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.CODE_PIC_URL +
                        "&space=" + spaceStr+"&style="+styleStr+"&local="+localStr+"&house="+houseStr
                        , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        Gson gson=new Gson();
                        ArrayList<CodePic> newCodePic = gson.fromJson(result, new TypeToken<ArrayList<CodePic>>() {
                        }.getType());
                        codePics.clear();
                        codePics.addAll(newCodePic);
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

        //局部spinner
        localAdapter= ArrayAdapter.createFromResource(getActivity(), R.array.codelocal, R.layout.spinner_item);
        localAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spanLocal.setAdapter(localAdapter);
        spanLocal.setPromptId(R.string.local_prompt);
        spanLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("btt","bb");
                if(position==0){
                    localStr="";
                }else {
                    localStr=spanLocal.getSelectedItem().toString();
                }
                HttpUtils utils=new HttpUtils();
                //http://localhost:8080/SweetHouse/BeatufulPicServlet
                // ?method=picSelect&space=&style=&local=&color=
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.CODE_PIC_URL +
                        "&space=" + spaceStr+"&style="+styleStr+"&local="+localStr+"&house="+houseStr
                        , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        Gson gson=new Gson();
                        ArrayList<CodePic> newCodePic = gson.fromJson(result, new TypeToken<ArrayList<CodePic>>() {
                        }.getType());
                        codePics.clear();
                        codePics.addAll(newCodePic);
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

        styleAdapter= ArrayAdapter.createFromResource(getActivity(), R.array.stylePic, R.layout.spinner_item);
        styleAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spanStyle.setAdapter(styleAdapter);
        spanStyle.setPromptId(R.string.style_prompt);
        spanStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("btt","bb");
                if(position==0){
                    styleStr="";
                }else {
                    styleStr=spanStyle.getSelectedItem().toString();
                }
                HttpUtils utils=new HttpUtils();
                //http://localhost:8080/SweetHouse/BeatufulPicServlet
                // ?method=picSelect&space=&style=&local=&color=
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.CODE_PIC_URL +
                        "&space=" + spaceStr+"&style="+styleStr+"&local="+localStr+"&house="+houseStr
                        , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        Gson gson=new Gson();
                        ArrayList<CodePic> newCodePic = gson.fromJson(result, new TypeToken<ArrayList<CodePic>>() {
                        }.getType());
                        codePics.clear();
                        codePics.addAll(newCodePic);
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

        //房型spinner
        houseAdapter= ArrayAdapter.createFromResource(getActivity(), R.array.house, R.layout.spinner_item);
        houseAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spanHouse.setAdapter(houseAdapter);
        spanHouse.setPromptId(R.string.house_prompt);
        spanHouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("btt", "bb");
                if (position == 0) {
                    houseStr = "";
                } else {
                    houseStr = spanHouse.getSelectedItem().toString();
                }
                HttpUtils utils = new HttpUtils();
                //http://localhost:8080/SweetHouse/BeatufulPicServlet
                // ?method=picSelect&space=&style=&local=&color=
                utils.send(HttpRequest.HttpMethod.GET, GlobalContacts.CODE_PIC_URL +
                        "&space=" + spaceStr + "&style=" + styleStr + "&local=" + localStr + "&house=" + houseStr
                        , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Gson gson = new Gson();
                        ArrayList<CodePic> newCodePic = gson.fromJson(result, new TypeToken<ArrayList<CodePic>>() {
                        }.getType());
                        codePics.clear();
                        codePics.addAll(newCodePic);
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

    //上拉刷新下拉加载

        lvCodePic.addFooterView(footView);
        mRefreshLayout.setChildView(lvCodePic);
        lvCodePic.setAdapter(adapter);
        lvCodePic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), CodePicDetailActivity.class);
                intent.putExtra("codepicid",codePics.get(position).getMpic_id());
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
    class CodePicAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return codePics.size();
        }

        @Override
        public Object getItem(int position) {
            return codePics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CodePicHolder holder;
            if (convertView==null){
                holder=new CodePicHolder();
                convertView=View.inflate(getActivity(),R.layout.gridview_codepic,null);
                holder.ivCodePic= (ImageView) convertView.findViewById(R.id.iv_pic);
                holder.tvDetail= (TextView) convertView.findViewById(R.id.tv_detail);
                holder.tvPicNum= (TextView) convertView.findViewById(R.id.tv_picnum);
                convertView.setTag(holder);
            }else {
                holder = (CodePicHolder) convertView.getTag();
            }
            BitmapUtils utils=new BitmapUtils(getActivity());
            utils.display(holder.ivCodePic, GlobalContacts.SERVER_URL + codePics.get(position).getMpic_pic());
            holder.tvDetail.setText(codePics.get(position).getMpic_detail());
            holder.tvPicNum.setText(codePics.get(position).getMpic_num());
            return convertView;
        }
    }
    class CodePicHolder{
        ImageView ivCodePic;
        TextView tvDetail;
        TextView tvPicNum;
    }
}
