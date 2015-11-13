package com.gwm.sweethouse.fragment.comment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.Comment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/7.
 */
public abstract class BaseCommentFragment extends Fragment {
    private ArrayList<Comment> comments;
    private GridViewWithHeaderAndFooter gvComment;
    private RefreshLayout mRefreshLayout;
    private GvCommentAdapter mAdapter;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_alls, null);
        Bundle arguments = getArguments();
        comments = (ArrayList<Comment>) arguments.getSerializable(getDatas());
        mAdapter = new GvCommentAdapter(comments, context);
        gvComment = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_comment);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        mRefreshLayout.setChildView(gvComment);
        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        gvComment.setAdapter(mAdapter);
        return view;
    }

    protected abstract String getDatas();

    class GvCommentAdapter extends MyBaseAdapter<Comment> {


        public GvCommentAdapter(ArrayList<Comment> datas, Context context) {
            super(datas, context);
        }

        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
            GvCommentViewHolder holder;
            if (convertView == null){
                convertView = View.inflate(context, R.layout.item_list_comment, null);
                holder = new GvCommentViewHolder();
                holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
                holder.tvUsername = (TextView) convertView.findViewById(R.id.tv_username);
                holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                convertView.setTag(holder);
            } else {
                holder = (GvCommentViewHolder) convertView.getTag();
            }
            Comment comment = datas.get(position);
            if (comment != null) {
                utils.display(holder.ivPic, GlobalContacts.SERVER_URL + comment.getUser_image());
                String user_name = comment.getUser_name();
                StringBuffer newStr = new StringBuffer();
                if (user_name.length() > 2){
                    char c = user_name.charAt(0);
                    char c1 = user_name.charAt(user_name.length() - 1);
                    LogUtils.d(c + ":::::::::::" + c1);
                    newStr.append(c+ "****" + c1);
                } else {
                    newStr.append("**");
                }
                holder.tvUsername.setText(newStr.toString());
                float grade = comment.getComment_grade();
                holder.ratingBar.setRating(grade);
                Date date = comment.getComment_time();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf.format(date);
                holder.tvTime.setText(time);
                String comment_content = comment.getComment_content();
                if (TextUtils.isEmpty(comment_content)){
                    if (grade >= 3.5 && grade <= 5.0) {
                        comment_content = "好评！";
                    }
                }
                holder.tvContent.setText(comment_content);
            }
            return convertView;
        }
    }

    class GvCommentViewHolder {
        ImageView ivPic;
        TextView tvUsername, tvTime, tvContent;
        RatingBar ratingBar;

    }
}
