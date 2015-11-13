package com.gwm.sweethouse;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gwm.sweethouse.fragment.order.Order1Fragment;
import com.gwm.sweethouse.fragment.order.Order2Fragment;
import com.gwm.sweethouse.fragment.order.Order3Fragment;
import com.gwm.sweethouse.fragment.order.Order4Fragment;
import com.gwm.sweethouse.fragment.order.Order5Fragment;
import com.umeng.message.PushAgent;

public class MyOrderActivity extends Activity {
    RadioGroup rg_order;
    RadioButton rb_oder1,rb_oder2,rb_oder3,rb_oder4,rb_oder5;
    ImageButton ibtn_return;
    FragmentTransaction transaction;
    Intent intent;

    private Order1Fragment order1Fragment;
    private Order2Fragment order2Fragment;
    private Order3Fragment order3Fragment;
    private Order4Fragment order4Fragment;
    private Order5Fragment order5Fragment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        initViews();
        initData();
        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(MyOrderActivity.this).onAppStart();
    }

    private void initData() {
        transaction = getFragmentManager().beginTransaction();
        intent = getIntent();
        int orderState = intent.getIntExtra("orderState",0);
        int user_id = intent.getIntExtra("user_id",0);
        Log.e("orderState",orderState+"0000000000");
        if(orderState==0){
            if (order1Fragment == null){
                order1Fragment = new Order1Fragment();
            }
            //默认选中第一个fragment
            transaction.add(R.id.layout_container,order1Fragment);
            transaction.commit();
        }else{
            switch (orderState){
                case 2:
                    order2Fragment = new Order2Fragment();
                    transaction.add(R.id.layout_container,order2Fragment);
                    transaction.commit();
                    rb_oder2.setChecked(true);
                    break;
                case 3:
                    order3Fragment = new Order3Fragment();
                    transaction.add(R.id.layout_container, order3Fragment);
                    transaction.commit();
                    rb_oder3.setChecked(true);
                    break;
                case 4:
                    order4Fragment = new Order4Fragment();
                    transaction.add(R.id.layout_container,order4Fragment);
                    transaction.commit();
                    rb_oder4.setChecked(true);
                    break;
                case 5:
                    order5Fragment = new Order5Fragment();
                    transaction.add(R.id.layout_container,order5Fragment);
                    transaction.commit();
                    rb_oder5.setChecked(true);
                    break;

            }
        }
    }

    private void initViews() {
        rg_order = (RadioGroup) findViewById(R.id.rg_order);
        rb_oder1 = (RadioButton) findViewById(R.id.rb_order1);
        rb_oder2 = (RadioButton) findViewById(R.id.rb_order2);
        rb_oder3 = (RadioButton) findViewById(R.id.rb_order3);
        rb_oder4 = (RadioButton) findViewById(R.id.rb_order4);
        rb_oder5 = (RadioButton) findViewById(R.id.rb_order5);
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_orderToMy);
        ibtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 finish();
            }
        });
        //默认选中第一个RadioButton
        rb_oder1.setChecked(true);

        rg_order.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radiogroup, int checkedId) {
                transaction = getFragmentManager().beginTransaction();
                switch (checkedId){
                    case R.id.rb_order1:
                        if (order1Fragment == null){
                            order1Fragment = new Order1Fragment();
                        }
                        transaction.replace(R.id.layout_container, order1Fragment);
                        transaction.commit();
                        break;
                    case R.id.rb_order2:
                        if (order2Fragment == null){
                            order2Fragment = new Order2Fragment();
                        }
                        transaction.replace(R.id.layout_container, order2Fragment);
                        transaction.commit();
                        break;
                    case R.id.rb_order3:
                        if (order3Fragment == null){
                            order3Fragment = new Order3Fragment();
                        }
                        transaction.replace(R.id.layout_container, order3Fragment);
                        transaction.commit();
                        break;

                    case R.id.rb_order4:
                        if (order4Fragment == null){
                            order4Fragment = new Order4Fragment();
                        }
                        transaction.replace(R.id.layout_container,order4Fragment);
                        transaction.commit();
                        break;
                    case R.id.rb_order5:
                        if (order5Fragment == null){
                            order5Fragment = new Order5Fragment();
                        }
                        transaction.replace(R.id.layout_container,order5Fragment);
                        transaction.commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
