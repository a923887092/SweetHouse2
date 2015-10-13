package com.gwm.sweethouse.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gwm.sweethouse.LoginActivity;
import com.gwm.sweethouse.MyCardActivity;
import com.gwm.sweethouse.MyPurseActivity;
import com.gwm.sweethouse.R;

/**
 * Created by Administrator on 2015/10/5.
 */
public class MyFragment extends Fragment {


    private Button button;
    private RadioGroup radioGroup;
    private RadioButton rbCard;
    private RadioButton rbPurse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_my,container,false);
        button = (Button) view.findViewById(R.id.btn_login);
        radioGroup = (RadioGroup) view.findViewById(R.id.rg_money);
        rbCard = (RadioButton) view.findViewById(R.id.rb_card);
        rbPurse = (RadioButton) view.findViewById(R.id.rb_purse);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==rbCard.getId()){
                    startActivity(new Intent(getActivity(),MyCardActivity.class));
                }else if(checkedId==rbPurse.getId()){
                    startActivity(new Intent(getActivity(),MyPurseActivity.class));
                }
            }
        });
        return view;

    }


}
