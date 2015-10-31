package com.gwm.sweethouse.fragment.saled;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwm.sweethouse.R;

/**
 * Created by Administrator on 2015/10/30.
 */
public class DiscountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_discount, null);
        return view;
    }
}
