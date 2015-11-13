package com.gwm.sweethouse.fragment.comment;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class AllsFragment extends BaseCommentFragment {
    @Override
    protected String getDatas() {
        return "allComments";
    }
}
