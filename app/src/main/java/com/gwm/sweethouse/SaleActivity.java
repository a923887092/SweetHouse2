package com.gwm.sweethouse;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gwm.sweethouse.fragment.saled.SaledFragment;

public class SaleActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new SaledFragment()).commit();
    }

}
