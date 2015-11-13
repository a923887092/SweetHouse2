package com.gwm.sweethouse.fragment.comment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwm.sweethouse.CommentActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.utils.LogUtils;

/**
 * Created by Administrator on 2015/11/6.
 */
public class GoodFragment extends BaseCommentFragment {

    @Override
    protected String getDatas() {
        return "goodComments";
    }
}
