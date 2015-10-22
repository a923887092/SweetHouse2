package com.gwm.sweethouse.fragment.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gwm.sweethouse.R;

/**
 * Created by Administrator on 2015/10/21.
 */
public class RightFragment extends Fragment {

    private ListView lvAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(inflater.getContext(), R.layout.fragment_right_menu, null);
        lvAddress = (ListView) view.findViewById(R.id.lv_address);
        return view;
    }
}
