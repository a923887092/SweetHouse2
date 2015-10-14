package com.gwm.sweethouse.fragment.mall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/14.
 */
public class ZmFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView view = new TextView(getActivity());
        view.setText("++++++++++++++");
        return view;
    }
}
