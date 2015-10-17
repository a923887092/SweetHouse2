package com.gwm.sweethouse.fragment.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwm.sweethouse.GoodsActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.SubClass;
import com.gwm.sweethouse.global.GlobalContacts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/14.
 */
public abstract class BaseMallFragment extends Fragment {

    private GridView gvSort;
    private ArrayList<SubClass> subClasses;
    public BaseMallFragment(ArrayList<SubClass> subClasses) {
        this.subClasses = subClasses;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.pager_mall_zm, null);
        initView(view);
        gvSort = (GridView) view.findViewById(R.id.gv_sort);
        if (subClasses != null){
            gvSort.setAdapter(new gvSortAdapter());
        }
        gvSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                intent.putExtra("xl", subClasses.get(position).getXl_id());
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    protected abstract void initView(View view);

    class gvSortAdapter extends MyBaseAdapter<SubClass> {

        public gvSortAdapter() {
            super(subClasses, getActivity());
        }

        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
            SubClassViewHolder holder;
            if (convertView == null){
                convertView = View.inflate(getActivity(), R.layout.item_list_subclass, null);
                holder = new SubClassViewHolder();
                holder.ivSub = (ImageView) convertView.findViewById(R.id.iv_sub);
                holder.tvSubName = (TextView) convertView.findViewById(R.id.tv_sub_name);
                convertView.setTag(holder);
            } else {
                holder = (SubClassViewHolder) convertView.getTag();
            }
            SubClass subClass = subClasses.get(position);
            holder.tvSubName.setText(subClass.getXl_name());
            utils.display(holder.ivSub, GlobalContacts.SERVER_URL + subClass.getXl_pic());
            return convertView;
        }
    }

    static class SubClassViewHolder {
        ImageView ivSub;
        TextView tvSubName;
    }
}
